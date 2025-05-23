package com.example.booktrackapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktrack.data.repository.auth.AuthRepository
import com.example.booktrack.data.response.ActiveAccResponse
import com.example.booktrack.data.response.LoginResponse
import com.example.booktrack.data.response.UserResponse
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.datastore.DataStoreManager
import com.example.booktrackapplication.data.request.ActiveAccRequest
import com.example.booktrackapplication.data.request.LoginRequest
import com.example.booktrackapplication.data.response.ProfileResponse
import com.example.booktrackapplication.utils.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class RegistrationViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel(), KoinComponent {

    var registrationUiState by mutableStateOf(RegistrationUiState())
        private set

    var loginstrationUiState by mutableStateOf(LoginUiState())
        private set

    private val _userUiState = MutableStateFlow(UserState())
    val userUiState: StateFlow<UserState> = _userUiState.asStateFlow()

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user.asStateFlow()


    var currentUser by mutableStateOf<UserResponse?>(null)

    fun onNisChange(newValue: String) {
        registrationUiState = registrationUiState.copy(
            nis = newValue,
            nisError = null
        )
    }

    fun onPhoneNumberChange(newValue: String) {
        registrationUiState = registrationUiState.copy(phoneNumber = newValue)
    }

    fun onOldPasswordChange(newValue: String) {
        registrationUiState = registrationUiState.copy(
            oldPassword = newValue,
            passwordError = null
        )
    }

    fun onNewPasswordChange(newValue: String) {
        registrationUiState = registrationUiState.copy(
            newPassword = newValue,
            newPasswordError = null
        )
    }


    fun onNisLoginChange(newValue: String) {
        loginstrationUiState = loginstrationUiState.copy(
            nis = newValue,
            nisError = null
        )
    }

    fun onPasswordChange(newValue: String) {
        loginstrationUiState = loginstrationUiState.copy(
            password = newValue,
            passError = null
        )
    }

    init {
        viewModelScope.launch {
            val savedUser = dataStoreManager.getUser()
            _user.value = savedUser
        }
    }

    fun activeAccount(onResult: (Boolean) -> Unit) {
        registrationUiState = registrationUiState.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = ActiveAccRequest(
                    nis = registrationUiState.nis,
                    password = registrationUiState.oldPassword,
                    newPassword = registrationUiState.newPassword,
                    phoneNumber = registrationUiState.phoneNumber
                )

                val result = authRepository.activeAccount(request)

                withContext(Dispatchers.Main) { // Kembali ke UI thread
                    when (result) {
                        is Resource.Success -> {
                            registrationUiState = registrationUiState.copy(
                                isLoading = false,
                                isSuccess = true,
                                response = result.data,
                                nisError = null,
                                passwordError = null
                            )
                            Log.d("RegistrationViewModel", "activeAccount: ${result.data}")
                            onResult(true) // Panggil callback jika sukses
                        }

                        is Resource.Error -> {
                            val message = result.message ?: "Terjadi kesalahan"

                            val nisError = if (message.contains("nis", true)) message else null
                            val passwordError = if (!message.contains("new password", true) && message.contains("password", true)) message else null
                            val numberPhoneError = if (
                                message.contains("phone", true) || message.contains("number", true)
                            ) message else null
                            val newPasswordError = if (message.contains("new password", true)) message else null

                            registrationUiState = registrationUiState.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage =  if (
                                    nisError == null &&
                                    passwordError == null &&
                                    numberPhoneError == null &&
                                    newPasswordError == null
                                ) message else null,
                                nisError = nisError,
                                passwordError = passwordError,
                                newPasswordError = newPasswordError,
                                generalError = numberPhoneError
                            )
                            Log.e("RegistrationViewModel", "activeAccount: ${result.message}")
                            onResult(false)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    registrationUiState = registrationUiState.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Terjadi kesalahan: ${e.message}"
                    )
                    Log.e("RegistrationViewModel", "activeAccount: Terjadi kesalahan", e)
                    onResult(false)
                }
            }
        }
    }


    fun login(onResult: (Boolean) -> Unit) {
        val nis = loginstrationUiState.nis
        val password = loginstrationUiState.password

        loginstrationUiState = loginstrationUiState.copy(
            isLoading = true,
            isError = false,
            errorMessage = null
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = LoginRequest(nis = nis, password = password)
                val result = authRepository.login(request)

                withContext(Dispatchers.Main) {
                    when (result) {
                        is Resource.Success -> {
                            val loginResponse = result.data
                            loginResponse.token?.let {
                                saveToken(it)
                            }

                            _user.value = loginResponse.user

                            loginResponse.user.let {
                                dataStoreManager.saveUser(it)
                            }

                            Log.d("book", "User setelah login: ${_user.value}")

                            loginstrationUiState = loginstrationUiState.copy(
                                isLoading = false,
                                isSuccess = true,
                                response = loginResponse
                            )
                            Log.d("book", "Login berhasil: ${loginResponse.user?.name}")
                            onResult(true)
                        }

                        is Resource.Error -> {
                            val message = result.message ?: "Terjadi kesalahan"

                            val nisError = if (message.contains("nis", true)) message else null
                            val passError = if (message.contains("password", true)) message else null

                            loginstrationUiState = loginstrationUiState.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = if (nisError == null && passError == null) message else null,
                                nisError = nisError,
                                passError = passError
                            )
                            Log.e("book", "Login gagal: ${result.message}")
                            onResult(false)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loginstrationUiState = loginstrationUiState.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Terjadi kesalahan: ${e.message}"
                    )
                    Log.e("book", "Exception login", e)
                    onResult(false)
                }
            }
        }
    }


    private fun saveToken(token: String) {
        viewModelScope.launch {
            val context = getKoin().get<Context>()
            context.dataStore.edit { preferences ->
                preferences[stringPreferencesKey("auth_token")] = token
            }
        }
    }

    fun logout(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            when (val result = authRepository.logout()) {
                is Resource.Success -> {
                    dataStoreManager.clearUser()
                    dataStoreManager.clearToken()
                    _user.value = null
                    onResult(true)
                }

                is Resource.Error -> {
                    Log.e("Logout", result.message)
                    onResult(false)
                }
            }
        }

    }

    fun getToken(): String? {
        var token: String? = null
        runBlocking {
            token = dataStoreManager.getToken()
        }
        return token
    }

    fun clearToken() {
        runBlocking {
            dataStoreManager.clearToken()
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _userUiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            when( val result = authRepository.getUser()) {
                is Resource.Success -> {
                    val user = result.data?.data

                    _userUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            user = user,
                        )
                    }
                }

                is Resource.Error -> {
                    _userUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Gagal memuat User"
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    fun clearError() {
        registrationUiState = registrationUiState.copy(
            isError = false,
            errorMessage = null
        )
    }


}

data class RegistrationUiState(
    val nis: String = "",
    val phoneNumber: String = "",
    val oldPassword: String = "",
    val newPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val nisError: String? = null,
    val passwordError: String? = null,
    val newPasswordError: String? = null,
    val generalError: String? = null,
    val response: ActiveAccResponse? = null
)

data class LoginUiState(
    val nis: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val nisError: String? = null,
    val passError: String? = null,
    val response: LoginResponse? = null
)

data class UserState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: ProfileResponse? = null
)