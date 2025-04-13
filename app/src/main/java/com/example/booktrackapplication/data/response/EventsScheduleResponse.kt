package com.example.booktrack.data.response

import com.example.booktrack.utils.toFormattedDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class EventsScheduleResponse (
    val message: String,
    val data: List<ScheduleItem>
)

@Serializable
data class ScheduleItem(
    val id: Int,
    val grade: Int,
    val department: String,
    @SerialName("class_index") val classIndex: Int,
    @SerialName("borrow_date") val borrowDate: String,
    @SerialName("return_date") val returnDate: String
) {
    val formattedBorrowDate : String
        get() = borrowDate.toFormattedDate()

    val formattedReturnDate : String
        get() = returnDate.toFormattedDate()
}