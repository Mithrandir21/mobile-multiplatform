package com.demo.remote.api

import com.demo.remote.api.models.RemoteAlbum
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface AlbumApi {

    @GET("/albums")
    fun getAlbums(): Single<List<RemoteAlbum>>

    @GET("/albums/{albumId}")
    fun getAlbum(@Path("albumId") albumId: Int): Maybe<RemoteAlbum>

}