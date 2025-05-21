package com.example.booktrackapplication.utils

fun convertToRoman(grade: String): String {
    return when (grade) {
        "10" -> "X"
        "11" -> "XI"
        "12" -> "XII"
        "13" -> "XIII"
        else -> grade
    }
}

fun extractAbbreviation(department: String): String {
    val regex = "\\((.*?)\\)".toRegex()
    return regex.find(department)?.groupValues?.get(1) ?: department
}
