package com.demo.remote.di

import com.demo.common.config.RemoteConfig
import com.demo.common.config.RemoteConfigurationProvider
import com.demo.common.di.Settings
import com.demo.common.storage.Storage
import com.demo.remote.RemoteConfigurationProviderImpl
import com.demo.remote.api.AlbumApi
import com.demo.remote.datasources.albums.RemoteAlbumsDataSource
import com.demo.remote.datasources.albums.RemoteAlbumsDataSourceImpl
import com.demo.remote.exceptions.RemoteExceptionTransformer
import com.demo.remote.exceptions.RemoteExceptionTransformerImpl
import com.demo.remote.logic.RemoteBuildUtil
import com.demo.remote.logic.RemoteBuildUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RemoteNetworkModule::class, InternalRemoteModule::class])
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    @RemoteInstance
    internal fun provideBuildUtil(): RemoteBuildUtil = RemoteBuildUtilImpl()

    @Provides
    @Singleton
    fun provideRemoteConfigurationProvider(@RemoteInstance remoteBuildUtil: RemoteBuildUtil, @Settings storage: Storage): RemoteConfigurationProvider =
        RemoteConfigurationProviderImpl(remoteBuildUtil, storage)

    @Provides
    @Singleton
    fun provideRemoteConfig(remoteConfigurationProvider: RemoteConfigurationProvider): RemoteConfig =
        remoteConfigurationProvider.getRemoteConfiguration()
}


@Module
@InstallIn(SingletonComponent::class)
internal class InternalRemoteModule {

    @Provides
    @Singleton
    fun provideRemoteExceptionTransformer(): RemoteExceptionTransformer = RemoteExceptionTransformerImpl()

    @Provides
    @Singleton
    fun provideRemoteAlbumsDataSource(
        albumApi: AlbumApi,
        remoteExceptionTransformer: RemoteExceptionTransformer
    ): RemoteAlbumsDataSource = RemoteAlbumsDataSourceImpl(albumApi, remoteExceptionTransformer)
}
