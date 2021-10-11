package com.demo.common.storage

import android.content.SharedPreferences
import com.demo.common.di.Settings
import com.demo.common.exceptions.DataExistsException
import com.demo.common.exceptions.DataNotFoundException
import com.demo.common.serializer.Serializer
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import javax.inject.Inject

internal class SettingStorage @Inject constructor(
    private val serializer: Serializer,
    @Settings val sharedPreferences: SharedPreferences,
) : Storage {

    override fun <T : Any> get(storageKey: String, deserializationStrategy: DeserializationStrategy<T>, defaultValue: T?): T =
        getNullable(storageKey, deserializationStrategy, defaultValue) ?: throw DataNotFoundException(storageKey)

    override fun <T : Any> getNullable(storageKey: String, deserializationStrategy: DeserializationStrategy<T>, defaultValue: T?): T? =
        sharedPreferences.getString(storageKey, null)
            ?.let { serializedData -> serializer.deserialize(serializedData, deserializationStrategy) }
            .let { data -> data ?: defaultValue }

    override fun <T : Any> save(storageKey: String, data: T, serializationStrategy: SerializationStrategy<T>, overwrite: Boolean): Boolean {
        if (!overwrite && containsKey(storageKey)) {
            throw DataExistsException(storageKey)
        }

        return sharedPreferences.edit().putString(storageKey, serializer.serialize(data, serializationStrategy)).commit()
    }

    override fun containsKey(storageKey: String): Boolean = sharedPreferences.contains(storageKey)

    override fun remove(storageKey: String): Boolean = sharedPreferences.edit().remove(storageKey).commit()
}