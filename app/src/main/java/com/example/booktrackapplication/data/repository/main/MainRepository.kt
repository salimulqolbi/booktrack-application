package com.example.booktrack.data.repository.main

import com.example.booktrack.data.response.BookResponse
import com.example.booktrack.data.response.BorrowStatusResponse
import com.example.booktrack.data.response.CurriculumResponse
import com.example.booktrack.data.response.EventsScheduleResponse
import com.example.booktrack.data.response.ValidateBorrowingDateResponse
import com.example.booktrack.utils.Resource

interface MainRepository {

    suspend fun checkBorrowStatus(): Resource<BorrowStatusResponse>

    suspend fun validateBorrowingDate(): Resource<ValidateBorrowingDateResponse>

    suspend fun getCurriculumByUser(): Resource<CurriculumResponse>

    suspend fun scanBookBarcode(code: String): Resource<BookResponse>

    suspend fun getSchedule(): Resource<EventsScheduleResponse>

}