package com.example.booktrackapplication.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryResponse(
    val message: String,
    val data: List<HistoryGroup>
)

@Serializable
data class HistoryGroup(
    val semester: Int,
    val books: List<HistoryItem>
)

@Serializable
data class HistoryItem(
    val id: Int,
    val title: String,
    @SerialName("book_type") val bookType: String,
    val grade: String,
    val department: String,
    val semesters: String,
    val code: String,
    @SerialName("cover_path") val coverPath: String,
    @SerialName("cover_url") val coverUrl: String,
    @SerialName("borrowed_by") val borrowedBy: BorrowedItemHistory,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class BorrowedItemHistory(
    val id: Int,
    val name: String,
    val grade: Int,
    val department: String,
    @SerialName("class_index")val classIndex: Int,
    @SerialName("phone_number") val phoneNumber: String
)