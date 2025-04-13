package com.example.booktrack.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BorrowStatusResponse (
    @SerialName("can_borrow") val canBorrow: Boolean,
   val message: String
)