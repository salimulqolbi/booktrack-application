package com.example.booktrackapplication.data.remote

import android.util.Log
import com.example.booktrack.data.response.BookResponse
import com.example.booktrack.data.response.BorrowStatusResponse
import com.example.booktrack.data.response.CurriculumResponse
import com.example.booktrack.data.response.EventsScheduleResponse
import com.example.booktrack.data.response.ValidateBorrowingDateResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
}