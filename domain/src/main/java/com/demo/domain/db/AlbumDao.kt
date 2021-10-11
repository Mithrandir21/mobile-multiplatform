package com.demo.domain.db

import androidx.room.*
import com.demo.domain.model.albums.Album
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe


@Dao
interface AlbumDao {

    /**
     * Returns a single [Album] with the given [id].
     * Note: Returning a List to allow for continuous observations, even if no results are found.
     */
    @Query("SELECT * FROM album WHERE album.id IS :id")
    fun getAlbum(id: Int): Flowable<List<Album>>

    /** Returns all the [Album]s in the database. */
    @Query("SELECT * FROM album")
    fun observeAllAlbums(): Flowable<List<Album>>

    /** Adds the [Album] to the database. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlbumCompletable(vararg genericItem: Album): Completable

    /** Deletes all [Album]s from the database. */
    @Query("DELETE FROM Album")
    fun clearAlbumsCompletable(): Completable


    /**
     * Deletes all [Album]s from the database.
     *
     * Note: Added non-reactive method for use in the non-reactive [clearExistingAndAdd] method.
     */
    @Query("DELETE FROM Album")
    fun clearAlbums()

    /**
     * Adds the [Album] to the database.
     *
     * Note: Added non-reactive method for use in the non-reactive [clearExistingAndAdd] method.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlbum(vararg genericItem: Album)

    /** Deletes all [Album]s from the database AND then inserts all items in the given list within a single [Transaction]. */
    @Transaction
    fun clearExistingAndAdd(genericItems: List<Album>) {
        clearAlbums()
        addAlbum(*genericItems.toTypedArray())
    }
}