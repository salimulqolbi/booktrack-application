package com.example.booktrackapplication.di

import android.util.Log
import com.example.booktrack.data.repository.auth.AuthRepository
import com.example.booktrack.data.repository.auth.AuthRepositoryImpl
import com.example.booktrack.data.repository.main.MainRepository
import com.example.booktrack.data.repository.main.MainRepositoryImpl
import com.example.booktrackapplication.data.datastore.DataStoreManager
import com.example.booktrackapplication.data.remote.AuthApiService
import com.example.booktrackapplication.data.remote.MainApiService
import com.example.booktrackapplication.utils.dataStore
import com.example.booktrackapplication.viewmodel.MainViewmodel
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { "http://116.254.117.235:8000/api" }
//    single { "http://103.196.153.74:8000/api" }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP Status:", "${response.status.value}")
                }
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    single { AuthApiService(get(), get()) }
    single { MainApiService(get(), get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<MainRepository> { MainRepositoryImpl(get(), get()) }

    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { MainViewmodel(get(), get()) }

    single { DataStoreManager(androidContext().dataStore) }
}