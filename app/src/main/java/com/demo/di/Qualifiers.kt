package com.demo.di

import javax.inject.Qualifier

/**
 * A [Qualifier] used specifically to differentiate between any dependency provided directly from any module and
 * a dependency provided through the apps modules, either as new instances or modified from the module instance.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppInstance