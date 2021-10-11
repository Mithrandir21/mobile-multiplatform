package com.demo.logging

import android.util.Log
import java.util.logging.Logger

/** Logging tag. */
inline val Any.tag: String get() = javaClass.simpleName

/** Logs a verbose entry using default [Logger] implementation. */
inline fun Any.verbose(throwable: Throwable? = null, messageProvider: () -> String) {
    Log.v(tag, messageProvider(), throwable)
}

/** Logs a debug entry using default [Logger] implementation. */
inline fun Any.debug(throwable: Throwable? = null, messageProvider: () -> String) {
    Log.d(tag, messageProvider(), throwable)
}

/** Logs an info entry using default [Logger] implementation. */
inline fun Any.info(throwable: Throwable? = null, messageProvider: () -> String) {
    Log.i(tag, messageProvider(), throwable)
}

/** Logs a warning entry using default [Logger] implementation. */
inline fun Any.warn(throwable: Throwable? = null, messageProvider: () -> String) {
    Log.w(tag, messageProvider(), throwable)
}

/** Logs an error entry using default [Logger] implementation. */
inline fun Any.error(throwable: Throwable? = null, messageProvider: () -> String) {
    Log.e(tag, messageProvider(), throwable)
}

/** Logs a fatal error entry using default [Logger] implementation. */
inline fun Any.fatal(throwable: Throwable? = null, messageProvider: () -> String) {
    Log.wtf(tag, messageProvider(), throwable)
}

enum class LogLevel(val level: Int) {
    VERBOSE(Log.VERBOSE),
    DEBUG(Log.DEBUG),
    INFO(Log.INFO),
    WARN(Log.WARN),
    ERROR(Log.ERROR),
    FATAL(8),
}
