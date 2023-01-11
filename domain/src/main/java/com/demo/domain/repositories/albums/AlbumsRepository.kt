package com.demo.domain.repositories.albums

import com.demo.domain.model.albums.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import java.util.*

interface AlbumsRepository {

    /**
     * Observers all [Album]s available to this repository, emitting all [Album]s when available data changes.
     */
    fun observeAlbums(): Flowable<List<Album>>

    /**
     * Observe a specific [Album] with the given [albumId] if present in available data.
     * If [Album] not present, [Optional] empty is returned.
     */
    fun observeAlbum(albumId: Int): Flowable<Optional<Album>>

    /**
     * [Completable] action that attempts to refresh the data available to the Repository.
     */
    fun refreshAlbums(): Completable

    /**
     * [Completable] action that attempts to refresh the data available to the Repository for a specific Album ID.
     */
    fun refreshAlbum(albumId: Int): Completable

}