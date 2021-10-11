package com.demo.logging.di

import com.demo.logging.Logger
import com.demo.logging.LoggerImpl
import com.demo.logging.implementations.SimpleLoggingListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoggingModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = LoggerImpl(mutableSetOf(SimpleLoggingListener()))
}