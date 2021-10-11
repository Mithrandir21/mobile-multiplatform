package com.demo.common.livedata

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry


@Suppress("unused")
class TestLifecycleOwner : LifecycleOwner {
    private val registry = LifecycleRegistry(this)

    val currentState: Lifecycle.State
        @NonNull
        get() = registry.currentState

    fun create(): TestLifecycleOwner = handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

    fun start(): TestLifecycleOwner = handleLifecycleEvent(Lifecycle.Event.ON_START)

    fun resume(): TestLifecycleOwner = handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    fun pause(): TestLifecycleOwner = handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

    fun stop(): TestLifecycleOwner = handleLifecycleEvent(Lifecycle.Event.ON_STOP)

    fun destroy(): TestLifecycleOwner = handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

    private fun handleLifecycleEvent(@NonNull event: Lifecycle.Event): TestLifecycleOwner {
        registry.handleLifecycleEvent(event)
        return this
    }

    @NonNull
    override fun getLifecycle(): Lifecycle = registry
}