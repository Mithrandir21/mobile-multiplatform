package com.demo.remote.api

import com.demo.remote.api.models.RemoteAlbum
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

internal interface AlbumApi {

    @GET("/albums")
    fun getAlbums(): Single<List<RemoteAlbum>>

}