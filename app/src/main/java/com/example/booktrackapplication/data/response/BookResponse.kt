package com.example.booktrack.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BookResponse(
    val message: String,
    val data: BookData
)

@Serializable
data class BookData(
    val id: Int,
    val title: String,
    @SerialName("book_type") val bookType: String,
    val grade: String,
    val department: String,
    val semesters: String,
    val code: String,
    @SerialName("cover_path") val coverPath: String,
    @SerialName("cover_url") val coverUrl: String,
    @SerialName("borrowed_by") val borrowedBy: BorrowerInfo? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedaAt: String
)

@Serializable
data class BorrowerInfo(
    val id: Int,
    val name: String,
    val grade: Int,
    val department: String,
    @SerialName("class_index") val classIndex: Int,
    @SerialName("phone_number") val phoneNumber: String
)