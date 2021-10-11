package com.demo.ui.albums.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.base.BaseViewModel
import com.demo.di.AppInstance
import com.demo.domain.model.albums.Album
import com.demo.domain.repositories.albums.AlbumsRepository
import com.demo.logging.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FragmentAlbumDetailsViewModel @Inject constructor(
    @AppInstance private val logger: Logger,
    private val albumsRepository: AlbumsRepository
) : BaseViewModel<FragmentAlbumDetailsViewModel.ActionType>() {

    private val viewData = MutableLiveData<Data>()

    fun observeViewData(): LiveData<Data> = viewData


    fun observeAlbum(albumId: Int) =
        Flowable.just(albumId)
            .doOnNext { logger.log { "Attempting to retrieve album data for ID: $it" } }
            .subscribeOn(Schedulers.io())
            .flatMap { albumsRepository.observeAlbum(it) }
            .doOnNext { startProgress(ActionType.LoadingAlbum) }
            .map { optionalData ->
                optionalData
                    .map<Data> { Data.AlbumData(it) }
                    .orElseGet { Data.NoAlbumData(albumId) }
            }
            .doOnNext { logger.log { "Album data: $it" } }
            .doOnNext { stopProgress(ActionType.LoadingAlbum) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(viewData::postValue) { error.postValue(it to ActionType.LoadingAlbum) }
            .addToDisposable()


    sealed class ActionType {
        object LoadingAlbum : ActionType()
    }

    sealed class Data {
        data class NoAlbumData(val albumID: Int) : Data()
        data class AlbumData(val album: Album) : Data()
    }
}