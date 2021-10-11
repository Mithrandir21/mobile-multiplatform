package com.demo.testing

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestingSharedPreferencesTest {

    @Test
    fun `test empty SharedPreferences`() {
        val testPref = TestingSharedPreferences()
        val all = testPref.all

        assertEquals(0, all.size)
    }

    @Test
    fun `test null SharedPreferences field`() {
        val key = "key"

        val testPref = TestingSharedPreferences()

        val empty = testPref.getString(key, null)

        assertNull(empty)
    }

    @Test
    fun `test value SharedPreferences field`() {
        val key = "key"
        val value = "fieldValue"

        val testPref = TestingSharedPreferences()
        testPref.edit().putString(key, value).apply()

        val retrievedValue = testPref.getString(key, null)

        assertNotNull(retrievedValue)
        assertEquals(value, retrievedValue)
    }

    @Test
    fun `test String Set SharedPreferences field`() {
        val key = "key"
        val value = setOf("fieldValue")

        val testPref = TestingSharedPreferences()
        testPref.edit().putStringSet(key, value).apply()

        val retrievedValue = testPref.getStringSet(key, null)

        assertNotNull(retrievedValue)
        assertEquals(value, retrievedValue)
    }

    @Test
    fun `test value SharedPreferences contains True`() {
        val key = "key"
        val value = "fieldValue"

        val testPref = TestingSharedPreferences()
        testPref.edit().putString(key, value).apply()

        val contains = testPref.contains(key)

        assertNotNull(contains)
        assertEquals(true, contains)
    }

    @Test
    fun `test value SharedPreferences contains False`() {
        val key = "key"
        val anotherKey = "anotherKey"
        val value = "fieldValue"

        val testPref = TestingSharedPreferences()
        testPref.edit().putString(key, value).apply()

        val contains = testPref.contains(anotherKey)

        assertNotNull(contains)
        assertEquals(false, contains)
    }

    @Test
    fun `test value SharedPreferences contains False after removal`() {
        val key = "key"
        val value = "fieldValue"

        val testPref = TestingSharedPreferences()
        testPref.edit().putString(key, value).apply()

        // First check
        val contains = testPref.contains(key)

        assertNotNull(contains)
        assertEquals(true, contains)

        // Remove value
        testPref.edit().remove(key).apply()

        // Second check
        val containsAfterRemoval = testPref.contains(key)

        assertNotNull(contains)
        assertEquals(false, containsAfterRemoval)
    }

    @Test
    fun `test value SharedPreferences contains False after clear`() {
        val key = "key"
        val value = "fieldValue"

        val testPref = TestingSharedPreferences()
        testPref.edit().putString(key, value).apply()

        // First check
        val contains = testPref.contains(key)

        assertNotNull(contains)
        assertEquals(true, contains)

        // Clear all value
        testPref.edit().clear().apply()

        // Second check
        val containsAfterRemoval = testPref.contains(key)

        assertNotNull(contains)
        assertEquals(false, containsAfterRemoval)
    }

    @Test
    fun `test value SharedPreferences replacement`() {
        val key = "key"
        val value = "fieldValue"
        val anotherValue = "anotherFieldValue"

        // First Value
        val testPref = TestingSharedPreferences()
        testPref.edit().putString(key, value).apply()

        val retrievedValue = testPref.getString(key, value)

        assertNotNull(retrievedValue)
        assertEquals(value, retrievedValue)


        // Second Value
        testPref.edit().putString(key, anotherValue).apply()

        val retrievedAnotherValue = testPref.getString(key, null)

        assertNotNull(retrievedAnotherValue)
        assertEquals(anotherValue, retrievedAnotherValue)
    }

    @Test(expected = ClassCastException::class)
    fun `test ClassCastException SharedPreferences retrieve wrong type`() {
        val key = "key"
        val value = 123

        val testPref = TestingSharedPreferences()
        testPref.edit().putInt(key, value).apply()

        testPref.getBoolean(key, false)
    }

    @Test
    fun `test value SharedPreferences change notified`() {
        val key = "key"
        val value = "fieldValue"


        val testPref = TestingSharedPreferences()

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            assertEquals(changedKey, key)

            val retrievedValue = testPref.getString(key, null)

            assertNotNull(retrievedValue)
            assertEquals(value, retrievedValue)
        }
        testPref.registerOnSharedPreferenceChangeListener(listener)

        testPref.edit().putString(key, value).apply()
    }

    @Test
    fun `test value SharedPreferences change not notified if value unchanged`() {
        val key = "key"
        val value = "fieldValue"

        val testPref = TestingSharedPreferences()

        // First notification
        val listener: SharedPreferences.OnSharedPreferenceChangeListener = mock()
        testPref.registerOnSharedPreferenceChangeListener(listener)

        testPref.edit().putString(key, value).apply()

        verify(listener, times(1)).onSharedPreferenceChanged(testPref, key)


        // Second notification
        testPref.edit().putString(key, value).apply()

        verifyNoMoreInteractions(listener)
    }

    @Test
    fun `test value SharedPreferences change not notified after listener removal`() {
        val key = "key"
        val value = "fieldValue"
        val anotherValue = "anotherValue"

        val testPref = TestingSharedPreferences()

        // First notification
        val listener: SharedPreferences.OnSharedPreferenceChangeListener = mock()
        testPref.registerOnSharedPreferenceChangeListener(listener)

        testPref.edit().putString(key, value).apply()

        verify(listener, times(1)).onSharedPreferenceChanged(testPref, key)


        // Second notification
        testPref.unregisterOnSharedPreferenceChangeListener(listener)

        testPref.edit().putString(key, anotherValue).apply()

        verifyNoMoreInteractions(listener)
    }
}