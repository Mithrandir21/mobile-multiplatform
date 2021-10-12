package com.demo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demo.common.livedata.LiveEvent
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

open class BaseViewModel<T> @Inject constructor() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val inProgress = LiveEvent<Pair<Boolean, T>>()
    private val error = LiveEvent<Pair<Throwable, T>>()

    fun observeProgress(): LiveData<Pair<Boolean, T>> = inProgress
    fun observeErrors(): LiveData<Pair<Throwable, T>> = error

    /*
     * While the [inProgress] LiveEvent will only fire the latest data with a
     * single Main threads cycle, we sometimes need to all access to app events
     * regardless of how quickly they are fired.
     */
    private val inProgressSubject = PublishSubject.create<Pair<Boolean, T>>()

    /**
     * [Flowable] that buffers all data so that no events are skipped because of
     * events being fired in quick succession unlike [LiveData].
     *
     * NOTE: [observeProgress] should be used to when it is needed to react to events.
     */
    fun observeProgressFlowable(): Flowable<Pair<Boolean, T>> = inProgressSubject.toFlowable(BackpressureStrategy.BUFFER)

    fun startProgress(type: T) {
        (true to type).let {
            inProgress.postValue(it)
            inProgressSubject.onNext(it)
        }
    }

    fun stopProgress(type: T) {
        (false to type).let {
            inProgress.postValue(it)
            inProgressSubject.onNext(it)
        }
    }

    fun reportError(throwable: Throwable, type: T) = error.postValue(throwable to type)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun Disposable.addToDisposable() {
        compositeDisposable.add(this)
    }
}