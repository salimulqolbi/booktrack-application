package com.example.booktrack.data.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserResponse
)

@Serializable
data class ErrorLoginResponse(
//    val message: Map<String, List<String>>
//    val message: String
    val message: JsonElement
)