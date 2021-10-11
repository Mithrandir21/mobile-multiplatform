package com.demo.common

import org.threeten.bp.Instant

/** Simple transformation from [Long] to [Instant] starting from Epoch start.*/
fun Long.toInstant(): Instant = Instant.ofEpochMilli(this)