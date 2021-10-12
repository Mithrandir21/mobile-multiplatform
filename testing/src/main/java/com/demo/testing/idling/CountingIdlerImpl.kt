package com.demo.testing.idling

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.espresso.idling.CountingIdlingResource
import com.demo.logging.Logger
import com.demo.logging.debug
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

internal class CountingIdlerImpl @Inject constructor(
    private val logger: Logger
) : CountingIdler {

    private val resourceName = CountingIdler::class.java.simpleName

    private val countingIdleResource = CountingIdlingResource(resourceName)

    /**
     * Reflects the internal counter of the [countingIdleResource] allowing accurate logging.
     */
    private val counter = AtomicInteger(0)

    override fun getIdler(): IdlingResource = countingIdleResource

    override fun getName(): String = resourceName

    override fun increment() {
        logger.debug { "Incrementing Idler by 1: ${counter.incrementAndGet()}" }
        countingIdleResource.increment()
    }

    override fun decrement() {
        logger.debug { "Decrementing Idler by 1: ${counter.decrementAndGet()}" }
        countingIdleResource.decrement()
    }

    override fun isIdle(): Boolean {
        val idle = countingIdleResource.isIdleNow
        logger.debug { "Checking if Idle (is: $idle)" }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        logger.debug { "Adding ResourceCallback" }
        countingIdleResource.registerIdleTransitionCallback(resourceCallback)
    }
}