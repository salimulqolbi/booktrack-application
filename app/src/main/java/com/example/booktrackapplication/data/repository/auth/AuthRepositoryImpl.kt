package com.example.booktrack.data.repository.auth

import android.content.res.Resources
//import com.example.booktrack.data.datastore.DataStoreManager
import com.example.booktrack.data.response.ActiveAccResponse
import com.example.booktrack.data.response.LoginResponse
import com.example.booktrack.data.response.UserResponse
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.datastore.DataStoreManager
import com.example.booktrackapplication.data.remote.AuthApiService
import com.example.booktrackapplication.data.request.ActiveAccRequest
import com.example.booktrackapplication.data.request.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl (
    private val apiService: AuthApiService,
    private val dataStoreManager: DataStoreManager
): AuthRepository {
    override suspend fun activeAccount(request: ActiveAccRequest): Resource<ActiveAccResponse> {
        return try {
            val response = apiService.activeAccount(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Gagal aktivasi: ${e.message}")
        }
    }

    override suspend fun login(request: LoginRequest): Resource<LoginResponse> {
        return try {
            val response = apiService.login(request)
            dataStoreManager.saveToken(response.token)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Gagal login: ${e.message}")
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            val token = dataStoreManager.getToken()
            if (token != null) {
                apiService.logout(token)
                dataStoreManager.clearToken()
                Resource.Success(Unit)
            } else {
                Resource.Error("Token tidak ditemukan")
            }
        } catch (e: Exception) {
            Resource.Error("Gagal logout: ${e.message}")
        }
    }

//    override suspend fun getUser(token: String): Resource<UserResponse> {
//        return try {
//            val response = apiService.getUser(token)
//            Resource.Success(response)
//        } catch (e: Exception) {
//            Resource.Error("Gagal mengambil data user: ${e.message}")
//        }
//    }
}