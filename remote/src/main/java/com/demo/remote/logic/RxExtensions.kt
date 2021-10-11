package com.demo.remote.logic

import com.demo.remote.exceptions.RemoteExceptionTransformer
import io.reactivex.rxjava3.core.*

/** Attempts to transform any known exception into a pre-defined exception. Otherwise, original exception is kept. */
internal fun Completable.transformKnownErrors(errorTransformer: RemoteExceptionTransformer): Completable =
    this.onErrorResumeNext { throwable -> Completable.error(errorTransformer.transformApiException(throwable)) }

/** Attempts to transform any known exception into a pre-defined exception. Otherwise, original exception is kept. */
internal fun <T> Single<T>.transformKnownErrors(errorTransformer: RemoteExceptionTransformer): Single<T> =
    this.onErrorResumeNext { throwable -> Single.error(errorTransformer.transformApiException(throwable)) }

/** Attempts to transform any known exception into a pre-defined exception. Otherwise, original exception is kept. */
internal fun <T> Maybe<T>.transformKnownErrors(errorTransformer: RemoteExceptionTransformer): Maybe<T> =
    this.onErrorResumeNext { throwable -> Maybe.error(errorTransformer.transformApiException(throwable)) }

/** Attempts to transform any known exception into a pre-defined exception. Otherwise, original exception is kept. */
internal fun <T> Observable<T>.transformKnownErrors(errorTransformer: RemoteExceptionTransformer): Observable<T> =
    this.onErrorResumeNext { throwable -> Observable.error(errorTransformer.transformApiException(throwable)) }

/** Attempts to transform any known exception into a pre-defined exception. Otherwise, original exception is kept. */
internal fun <T> Flowable<T>.transformKnownErrors(errorTransformer: RemoteExceptionTransformer): Flowable<T> =
    this.onErrorResumeNext { throwable -> Flowable.error(errorTransformer.transformApiException(throwable)) }