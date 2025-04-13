package com.example.booktrack.utils

//fun jurusanAcronym(jurusan: String): String {
//    return when (jurusan) {
//        "Sistem Informasi, Jaringan, dan Aplikasi" -> "SIJA"
//        "Teknik Elektronika" -> "TE"
//        "Teknik Kendaraan Ringan" -> "TKR"
//        "Teknik Instalasi Tenaga listrik" -> "TITL"
//        "Teknik Fabrikasi Logam dan Manufaktur" -> "TFLM"
//        "Konstruksi Jalan, Irigasi, dan Jembatan" -> "KJIJ"
//        "Konstruksi Gedung, dan Sanitasi" -> "KGS"
//        else -> "?"
//    }
//}

fun jurusanAcronym(jurusan: String): String {
    val regex = Regex("\\((.*?)\\)")
    return regex.find(jurusan)?.groups?.get(1)?.value ?: "?"
}

