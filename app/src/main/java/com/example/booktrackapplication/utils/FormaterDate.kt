package com.example.booktrack.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

//fun String.toFormattedDate(): String {
//    val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'X'")
//    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))
//
//    return try {
//        val dateTime = ZonedDateTime.parse(this, parser)
//        formatter.format(dateTime)
//    } catch (e: Exception) {
//        this // fallback kalau gagal format
//    }
//}

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

