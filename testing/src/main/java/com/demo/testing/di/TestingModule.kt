package com.demo.testing.di

import com.demo.logging.Logger
import com.demo.testing.idling.CountingIdler
import com.demo.testing.idling.CountingIdlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestingModule {

    @Provides
    @Singleton
    fun provideCountingIdler(logger: Logger): CountingIdler = CountingIdlerImpl(logger)
}