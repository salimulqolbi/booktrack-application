package com.example.booktrack.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActiveAccResponse(
    val message: String,
    val user: ActiveUserResponse
)

@Serializable
data class ActiveUserResponse(
    val id: Int,
    val name: String,
    val nis: String,
    @SerialName("email") val email: String?,
    @SerialName("phone_number") val phoneNumber: String,
    val status: String,
    val grade: Int,
    @SerialName("department") val departmentId: String,
    @SerialName("class_index") val classIndex: Int,
    val semester: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

