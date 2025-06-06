package com.example.booktrackapplication.data.remote

import android.util.Log
import com.example.booktrack.data.response.BookResponse
import com.example.booktrack.data.response.BorrowStatusResponse
import com.example.booktrack.data.response.CurriculumResponse
import com.example.booktrack.data.response.EventsScheduleResponse
import com.example.booktrack.data.response.UserResponse
import com.example.booktrack.data.response.ValidateBorrowingDateResponse
import com.example.booktrackapplication.data.response.ActivityResponse
import com.example.booktrackapplication.data.response.BookLoanRequest
import com.example.booktrackapplication.data.response.BookReturnRequest
import com.example.booktrackapplication.data.response.BorrowBooksResponse
import com.example.booktrackapplication.data.response.GetUserResponse
import com.example.booktrackapplication.data.response.HistoryResponse
import com.example.booktrackapplication.data.response.ProfileResponse
import com.example.booktrackapplication.data.response.ReturnBooksResponse
import com.example.booktrackapplication.data.response.ValidateReturningDateResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MainApiService (
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun checkBorrowStatus(token: String): BorrowStatusResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get{
                    url("$baseUrl/user/check-borrow-status")
                    headers{
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val body = response.bodyAsText()
                Log.d("BorrowApi", "checkBorrowStatus: $body")
                response.body()
            } catch (e: Exception) {
                Log.e("BorrowApi", "Error checkBorrowStatus: ${e.message}")
                throw e
            }
        }
    }

    suspend fun validateBorrowingDate (token: String): ValidateBorrowingDateResponse {
        return withContext(Dispatchers.IO) {
            try{
                val response: HttpResponse = client.get {
                    url("$baseUrl/events/validate-borrowing-date")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val body = response.bodyAsText()
                Log.d("BorrowApi", "validateBorrowingDate: $body")
                response.body()
            } catch (e: Exception) {
                Log.e("BorrowApi", "Error validateBorrowingDate: ${e.message}")
                throw e
            }
        }
    }

    suspend fun getCurriculumByUser(token: String): CurriculumResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get {
                    url("$baseUrl/curriculum/by-user")
                    headers{
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }
                val body = response.bodyAsText()
                Log.d("BorrowApi", "getCurriculumByUser: $body")
                response.body()
            } catch (e: ClientRequestException) {
                // Kalau 404 Curriculum tidak ditemukan
                val body = e.response.bodyAsText()
                Log.e("BorrowApi", "404 Not Found: $body")
                throw e
            } catch (e: Exception) {
                Log.e("BorrowApi", "Error getCurriculumByUser: ${e.message}")
                throw e
            }
        }
    }

    suspend fun scanBookBarcode(code: String, token: String): BookResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get {
                    url("$baseUrl/books/$code")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val body = response.bodyAsText()
                Log.d("ScanApi", "getBookByBarcode: $body")
                response.body()
            } catch (e: Exception) {
                Log.e("ScanApi", "Error getBookByBarcode: ${e.message}")
                throw e
            }
        }
    }

    suspend fun getSchedule(token: String): EventsScheduleResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get {
                    url("$baseUrl/events/active")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val body = response.bodyAsText()
                Log.d("ScheduleApi", "getSchedule: $body")
                response.body()
            } catch (e: Exception) {
                Log.e("ScheduleApi", "Error getSchedule: ${e.message}")
                throw e
            }
        }
    }

    suspend fun submitBorrowedBook(bookCodes: BookLoanRequest, token: String): BorrowBooksResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post{
                    url("$baseUrl/borrow-books")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                    setBody(bookCodes)
                }

                val body = response.bodyAsText()
                Log.d("LoanApi", "submitLoan: $body")
                response.body()
            } catch (e: ClientRequestException) {
                val errorBody = e.response.bodyAsText()
                Log.e("LoanApi", "submitLoan (client error): $errorBody")
                Json.decodeFromString<BorrowBooksResponse>(errorBody)
            } catch (e: Exception) {
                Log.e("LoanApi", "submitLoan error: ${e.message}")
                throw e
            }
        }
    }

    suspend fun getActivity(token: String): ActivityResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get {
                    url("$baseUrl/books/current")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val body = response.bodyAsText()
                Log.d("ActivityApi", "getActivity: $body")
                response.body()
            } catch (e: Exception) {
                Log.e("ActivityApi", "Error getActivity: ${e.message}")
                throw e
            }
        }
    }

    suspend fun getHistory(token: String): HistoryResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.get {
                    url("$baseUrl/books/history/all")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val body = response.bodyAsText()
                Log.d("HistoryApi", "getHistory: $body")
                response.body()
            } catch (e: Exception) {
                Log.e("HistoryApi", "Error getHistory: ${e.message}")
                throw e
            }
        }
    }

    suspend fun submitReturnedBook(bookCodes: BookReturnRequest, token: String): ReturnBooksResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post{
                    url("$baseUrl/return-books")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                    setBody(bookCodes)
                }

                val body = response.bodyAsText()
                Log.d("return", "returnBook: $body")
                response.body()
            } catch (e: ClientRequestException) {
                val errorBody = e.response.bodyAsText()
                Log.e("return", "returnBook (client error): $errorBody")
                Json.decodeFromString<ReturnBooksResponse>(errorBody)
            } catch (e: Exception) {
                Log.e("return", "returnBook error: ${e.message}")
                throw e
            }
        }
    }

    suspend fun getUser(token: String): GetUserResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response : HttpResponse = client.get {
                    url("$baseUrl/user")
                    headers{
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val body = response.bodyAsText()
                Log.d("UserApi", "getUser: $body")
                response.body()
            } catch (e: Exception) {
                Log.e("UserApi", "Error getUser: ${e.message}")
                throw e
            }
        }
    }
}