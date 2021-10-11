package com.demo.ui.preferences.remote

import androidx.lifecycle.LiveData
import com.demo.base.BaseViewModel
import com.demo.common.config.RemoteConfig
import com.demo.common.config.RemoteConfigurationProvider
import com.demo.common.livedata.LiveEvent
import com.demo.di.AppInstance
import com.demo.logging.Logger
import com.demo.logging.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RemotePreferencesViewModel @Inject constructor(
    @AppInstance private val logger: Logger,
    private val remoteConfigurationProvider: RemoteConfigurationProvider
) : BaseViewModel<RemotePreferencesViewModel.ActionType>() {

    private val viewData = LiveEvent<Data>()

    fun observeViewData(): LiveData<Data> = viewData


    fun loadRemoteEnvironment() =
        Single.just(true)
            .doOnSubscribe { startProgress(ActionType.LoadingRemoteEnvironment) }
            .subscribeOn(Schedulers.io())
            .map {
                val known = remoteConfigurationProvider.getKnownRemoteConfigurations()
                val current = remoteConfigurationProvider.getRemoteConfiguration()
                val currentIndex = known.indexOfFirst { it.configType == current.configType }

                return@map Data.PossibleRemoteEnvironments(known, currentIndex)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { stopProgress(ActionType.LoadingRemoteEnvironment) }
            .debugLog(logger)
            .subscribe(viewData::postValue) { error.postValue(it to ActionType.LoadingRemoteEnvironment) }
            .addToDisposable()

    fun setRemoteEnvironment(environment: RemoteConfig) =
        Single.just(environment)
            .doOnSubscribe { startProgress(ActionType.SetRemoteEnvironment) }
            .subscribeOn(Schedulers.io())
            .map { remoteConfigurationProvider.setRemoteConfiguration(it) }
            .map { Data.RemoteEnvironmentSet }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { stopProgress(ActionType.SetRemoteEnvironment) }
            .debugLog(logger)
            .subscribe(viewData::postValue) { error.postValue(it to ActionType.SetRemoteEnvironment) }
            .addToDisposable()


    sealed class ActionType {
        object LoadingRemoteEnvironment : ActionType()
        object SetRemoteEnvironment : ActionType()
    }

    sealed class Data {
        data class PossibleRemoteEnvironments(val possibles: List<RemoteConfig>, val currentIndex: Int) : Data()
        object RemoteEnvironmentSet : Data()
    }
}