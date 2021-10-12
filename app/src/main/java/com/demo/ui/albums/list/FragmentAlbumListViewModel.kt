package com.demo.ui.albums.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.base.BaseViewModel
import com.demo.di.AppInstance
import com.demo.domain.repositories.albums.AlbumsRepository
import com.demo.logging.Logger
import com.demo.logging.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FragmentAlbumListViewModel @Inject constructor(
    @AppInstance private val logger: Logger,
    private val albumsRepository: AlbumsRepository
) : BaseViewModel<FragmentAlbumListViewModel.ActionType>() {

    private val viewData = MutableLiveData<Data>()

    fun observeViewData(): LiveData<Data> = viewData

    fun startObservingAlbums() =
        albumsRepository.observeAlbums()
            .doOnNext { startProgress(ActionType.LoadingAlbums) }
            .map { albums -> albums.map { ModelAlbum(it) } }
            .map { Data.AlbumsData(it) }
            .doOnNext { logger.log { "Album data retrieved" } }
            .doOnNext { stopProgress(ActionType.LoadingAlbums) }
            .debugLog(logger, "Observing Albums from Dao")
            .subscribe(viewData::postValue) { reportError(it, ActionType.LoadingAlbums) }
            .addToDisposable()

    fun refreshAlbums() =
        albumsRepository.refreshAlbums()
            .doOnSubscribe { startProgress(ActionType.RefreshingAlbums) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { stopProgress(ActionType.RefreshingAlbums) }
            .debugLog(logger, "Refreshing Albums")
            .subscribe({ logger.log { "Successfully refreshed data" } }) { reportError(it, ActionType.RefreshingAlbums) }
            .addToDisposable()


    sealed class ActionType {
        object LoadingAlbums : ActionType()
        object RefreshingAlbums : ActionType()
    }

    sealed class Data {
        data class AlbumsData(val albums: List<ModelAlbum>) : Data()
    }
}