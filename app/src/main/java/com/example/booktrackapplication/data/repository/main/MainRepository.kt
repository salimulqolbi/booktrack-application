package com.example.booktrack.data.repository.main

import com.example.booktrack.data.response.BookResponse
import com.example.booktrack.data.response.BorrowStatusResponse
import com.example.booktrack.data.response.CurriculumResponse
import com.example.booktrack.data.response.EventsScheduleResponse
import com.example.booktrack.data.response.UserResponse
import com.example.booktrack.data.response.ValidateBorrowingDateResponse
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.response.ActivityResponse
import com.example.booktrackapplication.data.response.BookLoanRequest
import com.example.booktrackapplication.data.response.BookReturnRequest
import com.example.booktrackapplication.data.response.BorrowBooksResponse
import com.example.booktrackapplication.data.response.GetUserResponse
import com.example.booktrackapplication.data.response.HistoryResponse
import com.example.booktrackapplication.data.response.ReturnBooksResponse
import com.example.booktrackapplication.data.response.ValidateReturningDateResponse

interface MainRepository {

    suspend fun checkBorrowStatus(): Resource<BorrowStatusResponse>

    suspend fun validateBorrowingDate(): Resource<ValidateBorrowingDateResponse>

    suspend fun getCurriculumByUser(): Resource<CurriculumResponse>

    suspend fun scanBookBarcode(code: String): Resource<BookResponse>

    suspend fun submitBorrowedBook(bookCodes: BookLoanRequest): Resource<BorrowBooksResponse>

    suspend fun getActivity(): Resource<ActivityResponse>

    suspend fun getHistory(): Resource<HistoryResponse>

    suspend fun submitReturnedBook(bookCodes: BookReturnRequest): Resource<ReturnBooksResponse>

    suspend fun getUser(): Resource<GetUserResponse>

}