package com.example.booktrack.utils

fun jurusanAcronym(jurusan: String): String {
    val regex = Regex("\\((.*?)\\)")
    return regex.find(jurusan)?.groups?.get(1)?.value ?: "?"
}

