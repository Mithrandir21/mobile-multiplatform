package com.demo.remote.exceptions

import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_BAD_REQUEST
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_FORBIDDEN
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_METHOD_NOT_ALLOWED
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_NOT_FOUND
import com.demo.remote.exceptions.RemoteHttpException.Companion.CODE_UNAUTHORIZED
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class RemoteExternalExceptionsTest {

    @Test
    fun `test BadRequest Exception transformation`() = test(CODE_BAD_REQUEST, RemoteHttpException.BadRequest)

    @Test
    fun `test Unauthorized Exception transformation`() = test(CODE_UNAUTHORIZED, RemoteHttpException.Unauthorized)

    @Test
    fun `test Forbidden Exception transformation`() = test(CODE_FORBIDDEN, RemoteHttpException.Forbidden)

    @Test
    fun `test NotFound Exception transformation`() = test(CODE_NOT_FOUND, RemoteHttpException.NotFound)

    @Test
    fun `test MethodNotAllowed Exception transformation`() = test(CODE_METHOD_NOT_ALLOWED, RemoteHttpException.MethodNotAllowed)

    @Test
    fun `test general HttpException transformation`() = test(456789, RemoteHttpException.HttpException(456789))


    private fun <T> test(code: Int, expected: T) {
        val exception: HttpException = mock()

        whenever(exception.code()).thenReturn(code)

        val result = exception.toRemoteHttpException()

        assertEquals(expected, result)
    }
}