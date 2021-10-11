package com.demo.remote.exceptions

import retrofit2.HttpException

/**
 * Transformation functionality allowing for transformation between specific Remote module [Throwable]s and module-external [Throwable]s.
 */
internal interface RemoteExceptionTransformer {

    /**
     * Transform API exceptions, such as externally unavailable [HttpException], into specific exceptions that are available externally.
     */
    fun transformApiException(throwable: Throwable): Throwable

}