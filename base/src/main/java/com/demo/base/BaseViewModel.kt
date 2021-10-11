package com.demo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demo.common.livedata.LiveEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

open class BaseViewModel<T> @Inject constructor() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val inProgress = LiveEvent<Pair<Boolean, T>>()
    protected val error = LiveEvent<Pair<Throwable, T>>()

    fun observeProgress(): LiveData<Pair<Boolean, T>> = inProgress
    fun observeErrors(): LiveData<Pair<Throwable, T>> = error

    fun startProgress(type: T) = inProgress.postValue(true to type)
    fun stopProgress(type: T) = inProgress.postValue(false to type)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun Disposable.addToDisposable() {
        compositeDisposable.add(this)
    }
}