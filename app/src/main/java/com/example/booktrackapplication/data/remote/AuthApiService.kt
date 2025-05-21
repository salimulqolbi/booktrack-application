package com.example.booktrackapplication.data.remote

import android.util.Log
import com.example.booktrack.data.response.ActiveAccResponse
import com.example.booktrack.data.response.ErrorLoginResponse
import com.example.booktrack.data.response.ErrorResponse
import com.example.booktrack.data.response.LoginResponse
import com.example.booktrackapplication.data.request.ActiveAccRequest
import com.example.booktrackapplication.data.request.LoginRequest
import com.example.booktrackapplication.data.response.GetUserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AuthApiService(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun activeAccount(request: ActiveAccRequest): ActiveAccResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.put {
                    url("$baseUrl/auth/activate-account")
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }

                // Log response JSON sebelum parsing
                val responseBody = response.bodyAsText()
                Log.d("book", "Response Body: $responseBody")

                if(!response.status.isSuccess()) {
                    val errorJson = Json.decodeFromString<ErrorResponse>(responseBody)
                    throw Exception(errorJson.message)
                }

//                response.body() // Parsing ke ActiveAccResponse
                Json.decodeFromString(responseBody)
            } catch (e: Exception) {
                Log.e("book", "Failed to activate account: ${e.message}")
                throw Exception("${e.message}")
            }
        }
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return withContext(Dispatchers.IO) {
            try {

                val response: HttpResponse = client.post {
                    url("$baseUrl/auth/login")
                    contentType(ContentType.Application.Json)
                    header(HttpHeaders.Accept, "application/json")
                    setBody(request)
                }

                val responseBody = response.bodyAsText()
                Log.d("book", "Response Body: $responseBody")

                if(!response.status.isSuccess()) {
                    val errorJson = Json.decodeFromString<ErrorLoginResponse>(responseBody)
                    throw Exception(errorJson.message)
                }

//                response.body() // Parsing ke ActiveAccResponse
                Json.decodeFromString(responseBody)
            } catch (e: Exception) {
                Log.e("book", "Failed to login: ${e.message}")
                throw Exception("${e.message}")
            }
        }
    }

    suspend fun logout(token: String) {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("Logout", "Token yang dikirim: Bearer $token")

                val response: HttpResponse = client.post {
                    url("$baseUrl/auth/logout")
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                val responseBody = response.bodyAsText()
                Log.d("book", "Logout response: $responseBody")

                // Optional: bisa parse response ke model kalau perlu
                if (!response.status.isSuccess()) {
                    throw Exception("Logout gagal: ${response.status}")
                }
            } catch (e: Exception) {
                Log.e("book", "Failed to logout: ${e.message}")
                throw Exception("Failed to logout: ${e.message}")
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