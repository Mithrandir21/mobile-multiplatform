package com.demo.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.domain.model.albums.Album

@Database(version = 1, entities = [Album::class], exportSchema = false)
abstract class DomainDatabase : RoomDatabase() {

    abstract fun getAlbumDao(): AlbumDao

}