package com.demo.common.di

import android.content.Context
import android.content.SharedPreferences
import com.demo.common.serializer.Serializer
import com.demo.common.serializer.SerializerImpl
import com.demo.common.storage.SettingStorage
import com.demo.common.storage.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Settings
    fun provideSettingsSharedPreference(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences("demo_settings", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideKotlinSerializer(): Json = Json {
        encodeDefaults = true // Makes sure default field values are encoded
    }

    @Provides
    @Singleton
    fun provideSerializer(json: Json): Serializer = SerializerImpl(json)

    @Provides
    @Settings
    @Singleton
    fun provideSettingsStorage(serializer: Serializer, @Settings sharedPreferences: SharedPreferences): Storage = SettingStorage(serializer, sharedPreferences)
}