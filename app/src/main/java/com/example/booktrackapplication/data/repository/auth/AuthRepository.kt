package com.example.booktrack.data.repository.auth

import com.example.booktrack.data.response.ActiveAccResponse
import com.example.booktrack.data.response.LoginResponse
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.request.ActiveAccRequest
import com.example.booktrackapplication.data.request.LoginRequest
import com.example.booktrackapplication.data.response.GetUserResponse

interface AuthRepository {

    suspend fun activeAccount(request: ActiveAccRequest): Resource<ActiveAccResponse>

    suspend fun login(request: LoginRequest): Resource<LoginResponse>

    suspend fun getUser(): Resource<GetUserResponse>

    suspend fun logout(): Resource<Unit>

}