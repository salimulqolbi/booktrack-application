package com.example.booktrack.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurriculumResponse(
    val success: Boolean,
    val curriculum: Curriculum? = null,
    val message: String? = null
)

@Serializable
data class Curriculum(
    val id: Int,
    val grade: String,
    val department: Department,
    val semester: String,
    @SerialName("book_count") val bookCount: Int,
    @SerialName("book_borrow_quota") val bookBorrowQuota: Int
)

@Serializable
data class Department(
    val id: Int,
    val name: String
)
