package com.demo.common.storage

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.*
import com.demo.common.exceptions.DataExistsException
import com.demo.common.serializer.Serializer
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SettingStorageTest {

    @Mock
    private lateinit var serializer: Serializer

    @Mock
    private lateinit var sharedPreferences: SharedPreferences


    @Serializable
    data class CustomData(val data: String)

    @Test
    fun `test getting object with KClass`() {
        val key = "KEY"
        val data = CustomData("Data")
        val dataSerialized = "DataSerialized"

        whenever(sharedPreferences.getString(key, null)).thenReturn(dataSerialized)
        whenever(serializer.deserialize(eq(dataSerialized), any<DeserializationStrategy<CustomData>>())).thenReturn(data)

        val storage = SettingStorage(serializer, sharedPreferences)

        val result = storage.get(key, CustomData.serializer(), null)
        assertEquals(data, result)

        verify(sharedPreferences, times(1)).getString(key, null)
        verify(serializer, times(1)).deserialize(eq(dataSerialized), any<DeserializationStrategy<CustomData>>())
    }

    @Test
    fun `test getting list of object with ParameterizedType`() {
        val key = "KEY"
        val data = listOf(CustomData("Data"))
        val dataSerialized = "DataSerialized"

        whenever(sharedPreferences.getString(key, null)).thenReturn(dataSerialized)
        whenever(serializer.deserialize(eq(dataSerialized), any<KSerializer<List<CustomData>>>())).thenReturn(data)

        val storage = SettingStorage(serializer, sharedPreferences)

        val result = storage.get(key, ListSerializer(CustomData.serializer()), null)
        assertEquals(data, result)

        verify(sharedPreferences, times(1)).getString(key, null)
        verify(serializer, times(1)).deserialize(eq(dataSerialized), any<KSerializer<List<CustomData>>>())
    }

    @Test
    fun `test saving object with KClass`() {
        val key = "KEY"
        val data = CustomData("Data")
        val dataSerialized = "DataSerialized"

        val edit = Mockito.mock(SharedPreferences.Editor::class.java)

        whenever(edit.putString(key, dataSerialized)).thenReturn(edit)
        whenever(edit.commit()).thenReturn(true)
        whenever(sharedPreferences.edit()).thenReturn(edit)
        whenever(serializer.serialize(eq(data), any())).thenReturn(dataSerialized)

        val storage = SettingStorage(serializer, sharedPreferences)

        val result = storage.save(key, data, CustomData.serializer(), true)
        assertEquals(true, result)

        verify(serializer, times(1)).serialize(eq(data), any())
    }

    @Test
    fun `test saving list of object with ParameterizedType`() {
        val key = "KEY"
        val data = listOf(CustomData("Data"))
        val dataSerialized = "DataSerialized"

        val edit = Mockito.mock(SharedPreferences.Editor::class.java)

        whenever(edit.putString(key, dataSerialized)).thenReturn(edit)
        whenever(edit.commit()).thenReturn(true)
        whenever(sharedPreferences.edit()).thenReturn(edit)
        whenever(serializer.serialize(eq(data), any<KSerializer<List<CustomData>>>())).thenReturn(dataSerialized)

        val storage = SettingStorage(serializer, sharedPreferences)

        val result = storage.save(key, data, ListSerializer(CustomData.serializer()), true)
        assertEquals(true, result)

        verify(serializer, times(1)).serialize(eq(data), any<KSerializer<List<CustomData>>>())
    }

    @Test(expected = DataExistsException::class)
    fun `test fail object with KClass with overwrite`() {
        val key = "KEY"
        val data = CustomData("Data")

        whenever(sharedPreferences.contains(eq(key))).thenReturn(true)

        val storage = SettingStorage(serializer, sharedPreferences)

        storage.save(key, data, CustomData.serializer(), false)
    }

    @Test(expected = DataExistsException::class)
    fun `test fail list of object with ParameterizedType with overwrite`() {
        val key = "KEY"
        val data = listOf(CustomData("Data"))

        whenever(sharedPreferences.contains(key)).thenReturn(true)

        val storage = SettingStorage(serializer, sharedPreferences)

        storage.save(key, data, ListSerializer(CustomData.serializer()), false)
    }

    @Test
    fun `contains key true`() {
        val key = "KEY"

        whenever(sharedPreferences.contains(key)).thenReturn(true)

        val storage = SettingStorage(serializer, sharedPreferences)

        assertEquals(true, storage.containsKey(key))
    }

    @Test
    fun `contains key false`() {
        val key = "KEY"

        whenever(sharedPreferences.contains(key)).thenReturn(false)

        val storage = SettingStorage(serializer, sharedPreferences)

        assertEquals(false, storage.containsKey(key))
    }

    @Test
    fun `test remove key`() {
        val key = "KEY"

        val edit = Mockito.mock(SharedPreferences.Editor::class.java)

        whenever(edit.remove(key)).thenReturn(edit)
        whenever(edit.commit()).thenReturn(true)

        whenever(sharedPreferences.edit()).thenReturn(edit)

        val storage = SettingStorage(serializer, sharedPreferences)

        assertEquals(true, storage.remove(key))
    }
}