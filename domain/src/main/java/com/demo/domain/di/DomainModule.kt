package com.demo.domain.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.demo.domain.db.AlbumDao
import com.demo.domain.db.DomainDatabase
import com.demo.domain.repositories.albums.AlbumsRepository
import com.demo.domain.repositories.albums.AlbumsRepositoryImpl
import com.demo.remote.datasources.albums.RemoteAlbumsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [InternalDomainModule::class])
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideAlbumsRepository(albumDao: AlbumDao, remoteAlbumsDataSource: RemoteAlbumsDataSource): AlbumsRepository =
        AlbumsRepositoryImpl(albumDao, remoteAlbumsDataSource)

    @Provides
    @Domain
    fun provideSettingsSharedPreference(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences("demo_domain_storage", Context.MODE_PRIVATE)
}

@Module
@InstallIn(SingletonComponent::class)
internal class InternalDomainModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DomainDatabase =
        Room.databaseBuilder(context, DomainDatabase::class.java, "${DomainDatabase::class.java.simpleName}.db").build()

    @Provides
    @Singleton
    fun provideAlbumDao(db: DomainDatabase): AlbumDao = db.getAlbumDao()
}

