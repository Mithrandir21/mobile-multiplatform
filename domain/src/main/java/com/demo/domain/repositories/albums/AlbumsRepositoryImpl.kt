package com.demo.domain.repositories.albums

import com.demo.domain.db.AlbumDao
import com.demo.domain.model.albums.Album
import com.demo.domain.model.albums.toAlbum
import com.demo.remote.datasources.albums.RemoteAlbumsDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import java.util.*
import javax.inject.Inject

internal class AlbumsRepositoryImpl @Inject constructor(
    private val albumDao: AlbumDao,
    private val remoteAlbumsDataSource: RemoteAlbumsDataSource
) : AlbumsRepository {

    override fun observeAlbums(): Flowable<List<Album>> = albumDao.observeAllAlbums()

    override fun observeAlbum(albumId: Int): Flowable<Optional<Album>> =
        albumDao.getAlbum(albumId)
            .map {
                when(it.isEmpty()) {
                    true -> Optional.empty<Album>()
                    false -> Optional.of(it.first())
                }
            }

    override fun refreshAlbums(): Completable =
        remoteAlbumsDataSource.getAlbums()
            .toObservable()
            .flatMapIterable { it }
            .map { it.toAlbum() }
            .toList()
            .flatMapCompletable { Completable.fromAction { albumDao.clearExistingAndAdd(it) } }

    override fun refreshAlbum(albumId: Int): Completable =
        remoteAlbumsDataSource.getAlbum(albumId)
            .toObservable()
            .map { it.toAlbum() }
            .flatMapCompletable { albumDao.addAlbumCompletable(it) }
}