package com.example.booktrack.data.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserResponse
)

@Serializable
data class ErrorLoginResponse(val message: String)
