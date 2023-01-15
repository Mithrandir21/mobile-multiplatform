package com.demo.remote.exceptions

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class RemoteExceptionTransformerImplTest {

    @Test
    fun `test HttpException transformation`() {
        val code = 400
        val error = "Server HTTP Exception"

        val exception: HttpException = mock()
        val response: Response<*> = mock()
        val responseBody: ResponseBody = mock()

        val expected = RemoteHttpException.BadRequest

        whenever(exception.code()).thenReturn(code)

        val impl = RemoteExceptionTransformerImpl()

        val result = impl.transformApiException(exception)

        assertEquals(expected, result)
    }

    @Test
    fun `test General Exception transformation`() {
        val exception: RuntimeException = mock()

        val impl = RemoteExceptionTransformerImpl()

        val result = impl.transformApiException(exception)

        assertEquals(exception, result)
    }
}