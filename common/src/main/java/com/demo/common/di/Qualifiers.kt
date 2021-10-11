package com.demo.common.di

import javax.inject.Qualifier

/** A [Qualifier] used specifically for dependencies associated specifically with settings. */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Settings