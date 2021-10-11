package com.demo.domain.repositories.albums

import com.demo.domain.db.AlbumDao
import com.demo.domain.model.albums.Album
import com.demo.domain.model.albums.toAlbum
import com.demo.remote.api.models.RemoteAlbum
import com.demo.remote.datasources.albums.RemoteAlbumsDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AlbumsRepositoryImplTest {

    @Mock
    private lateinit var albumDao: AlbumDao

    @Mock
    private lateinit var remoteAlbumsDataSource: RemoteAlbumsDataSource


    @Test
    fun `test observing albums`() {
        val albumsList: List<Album> = mock()

        whenever(albumDao.observeAllAlbums()).thenReturn(Flowable.just(albumsList))

        AlbumsRepositoryImpl(albumDao, remoteAlbumsDataSource)
            .observeAlbums()
            .test()
            .assertNoErrors()
            .assertValue { it == albumsList }


        verify(albumDao, times(1)).observeAllAlbums()
    }

    @Test
    fun `test observing specific album present`() {
        val albumId = 1
        val album: Album = mock()

        whenever(albumDao.getAlbum(albumId)).thenReturn(Flowable.just(listOf(album)))

        AlbumsRepositoryImpl(albumDao, remoteAlbumsDataSource)
            .observeAlbum(albumId)
            .test()
            .assertNoErrors()
            .assertValue { it == Optional.of(album) }


        verify(albumDao, times(1)).getAlbum(albumId)
    }

    @Test
    fun `test observing specific album missing`() {
        val albumId = 1

        whenever(albumDao.getAlbum(albumId)).thenReturn(Flowable.just(listOf()))

        AlbumsRepositoryImpl(albumDao, remoteAlbumsDataSource)
            .observeAlbum(albumId)
            .test()
            .assertNoErrors()
            .assertValue { !it.isPresent }


        verify(albumDao, times(1)).getAlbum(albumId)
    }

    @Test
    fun `test refresh albums`() {
        val remoteAlbum = RemoteAlbum(1, 2, "Title")
        val remoteAlbums = listOf(remoteAlbum)
        val album = remoteAlbum.toAlbum()
        val albums = listOf(album)

        whenever(remoteAlbumsDataSource.getAlbums()).thenReturn(Single.just(remoteAlbums))

        AlbumsRepositoryImpl(albumDao, remoteAlbumsDataSource)
            .refreshAlbums()
            .test()
            .assertNoErrors()
            .assertComplete()


        verify(remoteAlbumsDataSource, times(1)).getAlbums()
        verify(albumDao, times(1)).clearExistingAndAdd(albums)
    }
}