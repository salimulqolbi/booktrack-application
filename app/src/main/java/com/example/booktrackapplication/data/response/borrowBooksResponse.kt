package com.example.booktrackapplication.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookLoanRequest(
    @SerialName("book_codes") val bookCodes: List<String>
)

@Serializable
data class BorrowBooksResponse(
    val success: Boolean,
    val message: String,
    val data: BorrowData? = null,
    val errors: BorrowErrors? = null
)

@Serializable
data class BorrowData(
    @SerialName("borrowed_count") val borrowedCount: Int
)

@Serializable
data class BorrowErrors(
    @SerialName("not_found_codes") val notFoundCodes: List<String>? = null,
    @SerialName("unavailable_books") val unavailableBooks: List<String>? = null
)