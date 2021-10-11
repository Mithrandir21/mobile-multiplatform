package com.demo.remote.exceptions

import com.demo.remote.exceptions.RemoteHttpException.*
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_BAD_REQUEST
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_FORBIDDEN
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_METHOD_NOT_ALLOWED
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_NOT_FOUND
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_UNAUTHORIZED
import retrofit2.HttpException

/*
 * Internal Note: This class is specifically created so that HttpExceptions produced by Retrofit can be propagated to other modules
 * without needing those modules to include the Retrofit library just to be able to receive Retrofit specific exceptions.
 */

/**
 * Sealed class that will represent specific HTTP exception that are results of API calls.
 */
sealed class RemoteHttpException(open val code: Int) : RuntimeException() {

    object BadRequest : RemoteHttpException(400)
    object Unauthorized : RemoteHttpException(401)
    object Forbidden : RemoteHttpException(403)
    object NotFound : RemoteHttpException(404)
    object MethodNotAllowed : RemoteHttpException(405)
    data class HttpException(override val code: Int) : RemoteHttpException(code)

    companion object {
        internal const val CODE_BAD_REQUEST = 400
        internal const val CODE_UNAUTHORIZED = 401
        internal const val CODE_FORBIDDEN = 403
        internal const val CODE_NOT_FOUND = 404
        internal const val CODE_METHOD_NOT_ALLOWED = 405
    }
}


internal fun HttpException.toRemoteHttpException(): RemoteHttpException =
    when (code()) {
        CODE_BAD_REQUEST -> BadRequest
        CODE_UNAUTHORIZED -> Unauthorized
        CODE_FORBIDDEN -> Forbidden
        CODE_NOT_FOUND -> NotFound
        CODE_METHOD_NOT_ALLOWED -> MethodNotAllowed
        else -> HttpException(code())
    }