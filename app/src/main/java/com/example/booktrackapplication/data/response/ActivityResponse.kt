package com.example.booktrackapplication.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityResponse(
    val message: String,
    val data: List<ActivityItem>
)

@Serializable
data class ActivityItem(
    val id: Int,
    val title: String,
    @SerialName("book_type") val bookType: String,
    val grade: String,
    val department: String,
    val semesters: String,
    val code: String,
    @SerialName("cover_path") val coverPath: String,
    @SerialName("cover_url") val coverUrl: String,
    @SerialName("borrowed_by") val borrowedBy: BorrowedItem,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class BorrowedItem(
    val id: Int,
    val name: String,
    val grade: Int,
    val department: String,
    @SerialName("class_index")val classIndex: Int,
    @SerialName("phone_number") val phoneNumber: String
)
