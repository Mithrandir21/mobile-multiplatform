package com.demo.remote.datasources.albums

import com.demo.remote.api.AlbumApi
import com.demo.remote.api.models.RemoteAlbum
import com.demo.remote.exceptions.RemoteExceptionTransformer
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class RemoteAlbumsDataSourceImplTest {

    @Mock
    private lateinit var albumApi: AlbumApi

    @Mock
    private lateinit var exceptionTransformer: RemoteExceptionTransformer


    @Test
    fun `test get album successfully`() {
        val mockAlbum: RemoteAlbum = mock()
        val albums = listOf(mockAlbum)

        whenever(albumApi.getAlbums()).thenReturn(Single.just(albums))

        RemoteAlbumsDataSourceImpl(albumApi, exceptionTransformer)
            .getAlbums()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it == albums }

        verifyNoMoreInteractions(exceptionTransformer)
        verify(albumApi, times(1)).getAlbums()
    }

    @Test
    fun `test get album failure - HttpException`() {
        val remoteException: HttpException = mock()
        val generalException: RuntimeException = mock()


        whenever(albumApi.getAlbums()).thenReturn(Single.error(remoteException))
        whenever(exceptionTransformer.transformApiException(remoteException)).thenReturn(generalException)

        RemoteAlbumsDataSourceImpl(albumApi, exceptionTransformer)
            .getAlbums()
            .test()
            .assertNoValues()
            .assertError(generalException)

        verify(exceptionTransformer, times(1)).transformApiException(remoteException)
        verify(albumApi, times(1)).getAlbums()
    }

    @Test
    fun `test get single album successfully`() {
        val albumId = 1
        val mockAlbum: RemoteAlbum = mock()

        whenever(albumApi.getAlbum(albumId)).thenReturn(Maybe.just(mockAlbum))

        RemoteAlbumsDataSourceImpl(albumApi, exceptionTransformer)
            .getAlbum(albumId)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it == mockAlbum }

        verifyNoMoreInteractions(exceptionTransformer)
        verify(albumApi, times(1)).getAlbum(albumId)
    }
}