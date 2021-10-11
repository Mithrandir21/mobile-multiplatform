package com.demo.remote.datasources.albums

import com.demo.remote.api.models.RemoteAlbum
import io.reactivex.rxjava3.core.Single

/**
 * Remote data source for retrieval of [RemoteAlbum]s.
 */
interface RemoteAlbumsDataSource {

    fun getAlbums(): Single<List<RemoteAlbum>>

}