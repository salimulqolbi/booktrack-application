package com.example.booktrack.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toFormattedDate(): String {
    return try {
        val instant = Instant.parse(this)
        val zoned = instant.atZone(ZoneId.of("Asia/Jakarta"))
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale("id", "ID"))
        formatter.format(zoned)
    } catch (e: Exception) {
        this
    }
}

