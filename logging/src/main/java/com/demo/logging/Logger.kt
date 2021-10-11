package com.demo.logging

/** Interface used to log messages and possible [Throwable] throughout the App. */
interface Logger {

    /**
     * Method used to log a specific message and possible [Throwable].
     *
     * Note that, depending on implementation, zero or more [LoggingInterface]s might be called to pass on the message and possible [Throwable].
     *
     * @param level The level of logging that applies to the provided message and possible [Throwable].
     * @param throwable A specific [Throwable] associated with this log event. This could be a fatal exception to some journey, or simply informative.
     * @param messageProvider The lambda function that will provide the message to be logged.
     */
    fun log(level: LogLevel = LogLevel.VERBOSE, throwable: Throwable? = null, messageProvider: () -> String)

    /**
     * A fatal [Throwable] has been thrown by the App. This happens when something catastrophic happens, something that cannot be recovered from.
     *
     * @param throwable The [Throwable] that has been thrown by the fatal event.
     */
    fun fatalThrowable(throwable: Throwable)

    /**
     * Add the provided [loggingInterface] to the [Set] of loggers that will receive log events.
     *
     * No exception will be thrown if the provided [loggingInterface] is already found in the existing set.
     */
    fun addLoggerListener(loggingInterface: LoggingInterface)

    /**
     * Remove the provided [loggingInterface] from the [Set] of loggers that will receive log events.
     *
     * No exception will be thrown if the provided [loggingInterface] is not found in the existing set.
     */
    fun removeLoggerListener(loggingInterface: LoggingInterface)
}