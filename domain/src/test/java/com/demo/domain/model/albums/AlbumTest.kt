package com.demo.domain.model.albums

import com.demo.remote.api.models.RemoteAlbum
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumTest {

    @Test
    fun `test RemoteAlbum to Album`() {
        val remoteAlbum = RemoteAlbum(1, 2, "Title")

        val album = remoteAlbum.toAlbum()

        assertEquals(remoteAlbum.id, album.id)
        assertEquals(remoteAlbum.userId, album.userId)
        assertEquals(remoteAlbum.title, album.title)
    }
}