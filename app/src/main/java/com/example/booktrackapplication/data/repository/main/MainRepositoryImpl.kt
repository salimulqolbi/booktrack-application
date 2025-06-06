package com.example.booktrack.data.repository.main

import android.util.Log
import com.example.booktrack.data.response.BookResponse
import com.example.booktrack.data.response.BorrowStatusResponse
import com.example.booktrack.data.response.CurriculumResponse
import com.example.booktrack.data.response.EventsScheduleResponse
import com.example.booktrack.data.response.UserResponse
import com.example.booktrack.data.response.ValidateBorrowingDateResponse
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.datastore.DataStoreManager
import com.example.booktrackapplication.data.remote.MainApiService
import com.example.booktrackapplication.data.response.ActivityResponse
import com.example.booktrackapplication.data.response.BookLoanRequest
import com.example.booktrackapplication.data.response.BookReturnRequest
import com.example.booktrackapplication.data.response.BorrowBooksResponse
import com.example.booktrackapplication.data.response.GetUserResponse
import com.example.booktrackapplication.data.response.HistoryResponse
import com.example.booktrackapplication.data.response.ReturnBooksResponse
import com.example.booktrackapplication.data.response.ValidateReturningDateResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import java.lang.Error

class MainRepositoryImpl(
    private val apiService: MainApiService,
    private val dataStoreManager: DataStoreManager
) : MainRepository {
    override suspend fun checkBorrowStatus(): Resource<BorrowStatusResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token tidak ditemukan")
            val response = apiService.checkBorrowStatus(token)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error checking borrow status")
        }
    }

    override suspend fun validateBorrowingDate(): Resource<ValidateBorrowingDateResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token Tidak Ditemukan")
            val response = apiService.validateBorrowingDate(token)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error validating borrowing date")
        }
    }

    override suspend fun getCurriculumByUser(): Resource<CurriculumResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token tidak ditemukan")
            val response = apiService.getCurriculumByUser(token)
            Resource.Success(response)
        } catch (e: ClientRequestException) {
            val errorMessage = e.response.bodyAsText()
            Resource.Error("Curriculum tidak ditemukan: $errorMessage")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan")
        }
    }

    override suspend fun scanBookBarcode(code: String): Resource<BookResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token tidak ditemukan")
            if (token == null) {
                Resource.Error("Token not found")
            } else {
                val result = apiService.scanBookBarcode(code, token)
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unexpected error")
        }
    }

    override suspend fun getSchedule(): Resource<EventsScheduleResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("TOken tidak ditemukan")
            Log.d("SCHEDULE", "Token: $token")
            if (token == null) {
                Resource.Error("Token not found")
            } else {
                val result = apiService.getSchedule(token)
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unexpected error")
        }
    }

    override suspend fun submitBorrowedBook(
        bookCodes: BookLoanRequest
    ): Resource<BorrowBooksResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token not found")
            val result = apiService.submitBorrowedBook(bookCodes, token)
            if(result.success) {
                Resource.Success(result)
            } else {
                Resource.Error(result.message)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Gagal melakukan peminjaman")
        }
    }

    override suspend fun getActivity(): Resource<ActivityResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token not found")
            val result = apiService.getActivity(token)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Gagal mendapatkan aktivitas")
        }
    }

    override suspend fun getHistory(): Resource<HistoryResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token Not Found")
            val result = apiService.getHistory(token)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Gagal mendapatkan riwayat")
        }
    }

    override suspend fun validateReturningDate(): Resource<ValidateReturningDateResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token not found")
            val response = apiService.validateReturningDate(token)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error validating returning date")
        }
    }

    override suspend fun submitReturnedBook(bookCodes: BookReturnRequest): Resource<ReturnBooksResponse> {
        return try {
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token not found")
            val result = apiService.submitReturnedBook(bookCodes, token)
            if(result.success) {
                Resource.Success(result)
            } else {
//                Resource.Error(result.message)
//                val json = Json.encodeToString(result)
                Resource.Error(result.message, result)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Gagal melakukan pengembalian")
        }
    }

    override suspend fun getUser(): Resource<GetUserResponse> {
        return try{
            val token = dataStoreManager.getToken() ?: return Resource.Error("Token not found")
            val result = apiService.getUser(token)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Gagal mendapatkan data user")
        }
    }
}