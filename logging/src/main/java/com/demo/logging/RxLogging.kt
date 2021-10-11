package com.demo.logging

import io.reactivex.rxjava3.core.*

fun Completable.debugLog(logger: Logger, identification: String = System.currentTimeMillis().toString()): Completable = this
    .doOnSubscribe { logger.log(LogLevel.DEBUG) { "Completable onSubscribe ($identification)" } }
    .doOnComplete { logger.log(LogLevel.DEBUG) { "Completable onComplete ($identification)" } }
    .doOnError { logger.log(LogLevel.ERROR, it) { "Completable onError ($identification) - Error: $it" } }
    .doOnDispose { logger.log(LogLevel.DEBUG) { "Completable onDispose ($identification)" } }
    .doOnTerminate { logger.log(LogLevel.DEBUG) { "Completable onTerminate ($identification)" } }
    .doAfterTerminate { logger.log(LogLevel.DEBUG) { "Completable onAfterTerminate ($identification)" } }
    .doFinally { logger.log(LogLevel.DEBUG) { "Completable onFinally ($identification)" } }

fun <T> Single<T>.debugLog(logger: Logger, identification: String = System.currentTimeMillis().toString()): Single<T> = this
    .doOnSubscribe { logger.log(LogLevel.DEBUG) { "Single onSubscribe ($identification)" } }
    .doOnSuccess { logger.log(LogLevel.DEBUG) { "Single onSuccess ($identification) - Data: $it" } }
    .doOnError { logger.log(LogLevel.ERROR, it) { "Single onError ($identification) - Error: $it" } }
    .doOnDispose { logger.log(LogLevel.DEBUG) { "Single onDispose ($identification)" } }
    .doAfterSuccess { logger.log(LogLevel.DEBUG) { "Single onAfterSuccess ($identification)" } }
    .doAfterTerminate { logger.log(LogLevel.DEBUG) { "Single onAfterTerminate ($identification)" } }
    .doFinally { logger.log(LogLevel.DEBUG) { "Single onFinally ($identification)" } }

fun <T> Maybe<T>.debugLog(logger: Logger, identification: String = System.currentTimeMillis().toString()): Maybe<T> = this
    .doOnSubscribe { logger.log(LogLevel.DEBUG) { "Maybe onSubscribe ($identification)" } }
    .doOnSuccess { logger.log(LogLevel.DEBUG) { "Maybe onSuccess ($identification) - Data: $it" } }
    .doOnComplete { logger.log(LogLevel.DEBUG) { "Maybe onComplete ($identification)" } }
    .doOnError { logger.log(LogLevel.ERROR, it) { "Maybe onError ($identification) - Error: $it" } }
    .doOnDispose { logger.log(LogLevel.DEBUG) { "Maybe onDispose ($identification)" } }
    .doAfterSuccess { logger.log(LogLevel.DEBUG) { "Maybe onAfterSuccess ($identification)" } }
    .doAfterTerminate { logger.log(LogLevel.DEBUG) { "Maybe onAfterTerminate ($identification)" } }
    .doFinally { logger.log(LogLevel.DEBUG) { "Maybe onFinally ($identification)" } }

fun <T> Observable<T>.debugLog(logger: Logger, identification: String = System.currentTimeMillis().toString()): Observable<T> = this
    .doOnSubscribe { logger.log(LogLevel.DEBUG) { "Observable onSubscribe ($identification)" } }
    .doOnNext { logger.log(LogLevel.DEBUG) { "Observable onNext ($identification) - Data: $it" } }
    .doOnComplete { logger.log(LogLevel.DEBUG) { "Observable onComplete ($identification)" } }
    .doOnError { logger.log(LogLevel.ERROR, it) { "Observable onError ($identification) - Error: $it" } }
    .doOnDispose { logger.log(LogLevel.DEBUG) { "Observable onDispose ($identification)" } }
    .doOnTerminate { logger.log(LogLevel.DEBUG) { "Observable onTerminate ($identification)" } }
    .doAfterNext { logger.log(LogLevel.DEBUG) { "Observable onAfterNext ($identification) - Data: $it" } }
    .doAfterTerminate { logger.log(LogLevel.DEBUG) { "Observable onAfterTerminate ($identification)" } }
    .doFinally { logger.log(LogLevel.DEBUG) { "Observable onFinally ($identification)" } }

fun <T> Flowable<T>.debugLog(logger: Logger, identification: String = System.currentTimeMillis().toString()): Flowable<T> = this
    .doOnSubscribe { logger.log(LogLevel.DEBUG) { "Flowable onSubscribe ($identification)" } }
    .doOnNext { logger.log(LogLevel.DEBUG) { "Flowable onNext ($identification) - Data: $it" } }
    .doOnComplete { logger.log(LogLevel.DEBUG) { "Flowable onComplete ($identification)" } }
    .doOnError { logger.log(LogLevel.ERROR, it) { "Flowable onError ($identification) - Error: $it" } }
    .doOnCancel { logger.log(LogLevel.DEBUG) { "Flowable onCancel ($identification)" } }
    .doOnTerminate { logger.log(LogLevel.DEBUG) { "Flowable onTerminate ($identification)" } }
    .doOnRequest { logger.log(LogLevel.DEBUG) { "Flowable onRequest ($identification)" } }
    .doAfterNext { logger.log(LogLevel.DEBUG) { "Flowable onAfterNext ($identification) - Data: $it" } }
    .doAfterTerminate { logger.log(LogLevel.DEBUG) { "Flowable onAfterTerminate ($identification)" } }
    .doFinally { logger.log(LogLevel.DEBUG) { "Flowable onFinally ($identification)" } }

