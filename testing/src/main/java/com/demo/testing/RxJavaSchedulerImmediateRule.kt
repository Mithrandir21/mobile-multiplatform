package com.demo.testing

import androidx.annotation.NonNull
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/** This rule will force all Rx Threads to perform in immediately. */
class RxJavaSchedulerImmediateRule : TestWatcher() {

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(@NonNull run: Runnable): Disposable = super.scheduleDirect(run, 0, TimeUnit.MILLISECONDS)
        override fun createWorker(): Worker =
            ExecutorScheduler.ExecutorWorker(object : ScheduledThreadPoolExecutor(1) {
                override fun execute(runnable: Runnable) = runnable.run()
            }, true, true)
    }

    override fun starting(description: Description?) {
        super.starting(description)

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}