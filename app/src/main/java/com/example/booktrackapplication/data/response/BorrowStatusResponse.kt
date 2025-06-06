package com.example.booktrack.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BorrowStatusResponse (
    @SerialName("can_borrow") val canBorrow: Boolean,
    @SerialName("current_borrowed") val currentBorrowed: Int? = null,
    @SerialName("borrow_quota") val borrowQuota: Int? = null,
   val message: String? = null
)

@Serializable
class BorrowStatusErrorResponse (
    @SerialName("can_borrow") val canBorrow: Boolean,
    val message: String
)