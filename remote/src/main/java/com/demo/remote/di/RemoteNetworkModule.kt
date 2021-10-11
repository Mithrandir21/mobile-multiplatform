package com.demo.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.demo.common.config.RemoteConfigurationProvider
import com.demo.remote.api.AlbumApi
import com.demo.remote.logic.RemoteBuildUtil
import com.demo.remote.logic.RemoteBuildType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteNetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@RemoteInstance remoteBuildUtil: RemoteBuildUtil): OkHttpClient {
        val builder = OkHttpClient.Builder()

        when (remoteBuildUtil.buildType()) {
            RemoteBuildType.DEBUG -> builder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            RemoteBuildType.RELEASE -> Unit
        }

        return builder
            .connectTimeout(3, TimeUnit.SECONDS) // TODO - Place durations into RemoteConfiguration
            .build()
    }

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideRetrofit(remoteConfigurationProvider: RemoteConfigurationProvider, okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(remoteConfigurationProvider.getRemoteConfiguration().baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideAlbumApi(retrofit: Retrofit): AlbumApi = retrofit.create(AlbumApi::class.java)
}