package com.example.booktrackapplication.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidateReturningDateResponse(
    val valid: Boolean,
    val message: String,
    @SerialName("event_id") val eventId: Int? = null
)
