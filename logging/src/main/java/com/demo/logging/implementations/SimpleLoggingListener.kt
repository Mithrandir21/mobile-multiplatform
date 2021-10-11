package com.demo.logging.implementations

import com.demo.logging.*
import javax.inject.Inject

internal class SimpleLoggingListener @Inject constructor() : LoggingInterface {

    /** Returns a boolean indicating whether this [LoggingInterface] is enabled or not. */
    override fun isEnabled(): Boolean = true

    /** Tag for the interface used internally for reporting or identifying. */
    override fun getLoggerTag(): String = SimpleLoggingListener::class.java.simpleName

    /**
     * Method called when some information needs logging.
     *
     * @param level The level of logging that applies to the provided message and possible [Throwable].
     * @param message The logged message.
     * @param throwable A specific [Throwable] associated with this log event. This could be a fatal exception to some journey, or simply informative.
     */
    override fun onLog(level: LogLevel, message: String, throwable: Throwable?) =
        when (level) {
            LogLevel.VERBOSE -> verbose(throwable) { message }
            LogLevel.DEBUG -> debug(throwable) { message }
            LogLevel.INFO -> info(throwable) { message }
            LogLevel.WARN -> warn(throwable) { message }
            LogLevel.ERROR -> error(throwable) { message }
            LogLevel.FATAL -> fatal(throwable) { message }
        }

    /**
     * A fatal [Throwable] has been thrown by the App. This happens when something catastrophic happens, something that cannot be recovered from.
     *
     * @param throwable The [Throwable] that has been thrown by the fatal event.
     */
    override fun onFatalThrowable(throwable: Throwable) = fatal(throwable) { "Fatal crash" }
}