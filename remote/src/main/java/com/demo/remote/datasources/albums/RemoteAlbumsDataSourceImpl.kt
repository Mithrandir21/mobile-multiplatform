package com.demo.remote.datasources.albums

import com.demo.remote.api.AlbumApi
import com.demo.remote.api.models.RemoteAlbum
import com.demo.remote.exceptions.RemoteExceptionTransformer
import com.demo.remote.logic.transformKnownErrors
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Remote-Module internal implementation of remote data source for retrieval of [RemoteAlbum]s.
 */
internal class RemoteAlbumsDataSourceImpl @Inject constructor(
    private val storesApi: AlbumApi,
    private val remoteExceptionTransformer: RemoteExceptionTransformer
) : RemoteAlbumsDataSource {

    override fun getAlbums(): Single<List<RemoteAlbum>> =
        storesApi.getAlbums()
            .transformKnownErrors(remoteExceptionTransformer)
}