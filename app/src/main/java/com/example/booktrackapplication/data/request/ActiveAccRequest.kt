package com.example.booktrackapplication.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActiveAccRequest(
    val nis: String,
    val password: String,
    @SerialName("new_password") val newPassword: String,
    @SerialName("phone_number") val phoneNumber: String,
)
