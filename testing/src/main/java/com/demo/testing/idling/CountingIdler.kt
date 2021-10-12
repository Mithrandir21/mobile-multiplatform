package com.demo.testing.idling

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Interface for [CountingIdlingResource] and allowing for replacement for testing purposed.
 */
interface CountingIdler {

    /**
     * Returns the name of the resources (used for logging and idempotency of registration).
     */
    fun getName(): String

    /**
     * Increments the count of in-flight transactions to the resource being monitored.
     */
    fun increment()

    /**
     * Decrements the count of in-flight transactions to the resource being monitored.
     */
    fun decrement()

    /**
     * Returns true if resource is currently idle.
     *
     * Basically if the internal counter is at 0, meaning there is nothing to wait for.
     */
    fun isIdle(): Boolean

    /**
     * Registers the given [IdlingResource.ResourceCallback] with the resource.
     */
    fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback)

    /**
     * Get the actual [IdlingResource]. Used specifically to allow tests to register the idler.
     */
    fun getIdler(): IdlingResource
}