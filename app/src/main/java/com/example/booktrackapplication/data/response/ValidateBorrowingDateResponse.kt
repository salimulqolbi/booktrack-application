package com.example.booktrack.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ValidateBorrowingDateResponse(
    val valid: Boolean,
    val message: String,
    @SerialName("event_id") val eventId: Int? = null
)