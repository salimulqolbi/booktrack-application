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
import com.example.booktrackapplication.data.response.GetUserResponse
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
            Resource.Error("${e.message}")
        }
    }

    override suspend fun login(request: LoginRequest): Resource<LoginResponse> {
        return try {
            val response = apiService.login(request)
            dataStoreManager.saveToken(response.token)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("${e.message}")
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