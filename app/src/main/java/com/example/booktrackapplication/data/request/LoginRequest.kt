package com.example.booktrackapplication.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val nis: String,
    val password: String
)