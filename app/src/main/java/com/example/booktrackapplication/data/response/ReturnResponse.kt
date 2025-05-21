package com.example.booktrackapplication.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookReturnRequest(
    @SerialName("book_codes") val bookCodes: List<String>
)

@Serializable
data class ReturnBooksResponse(
    val success: Boolean,
    val message: String,
    val data: ReturnData? = null,
    val errors: BorrowErrors? = null
)

@Serializable
data class ReturnData(
    @SerialName("returned_count") val borrowedCount: Int
)

@Serializable
data class ReturnErrors(
    @SerialName("not_found_codes") val notFoundCodes: List<String>? = null,
    @SerialName("invalid_codes") val invalidCodes: List<String>? = null
)