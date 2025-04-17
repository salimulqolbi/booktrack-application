package com.example.booktrackapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktrack.data.repository.auth.AuthRepository
import com.example.booktrack.data.repository.main.MainRepository
import com.example.booktrack.data.response.BookData
import com.example.booktrack.data.response.Curriculum
import com.example.booktrack.data.response.ScheduleItem
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.response.BookLoanRequest
import com.example.booktrackapplication.data.response.BorrowBooksResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class MainViewmodel(
    private val mainRepository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(BorrowUiState())
    val uiState: StateFlow<BorrowUiState> = _uiState.asStateFlow()

    private val _loanedBooks = mutableStateListOf<BookData>()
    val loanedBooks: List<BookData> = _loanedBooks

    private val _searchHistory = mutableStateListOf<String>()
    val searchHistory: List<String> get() = _searchHistory

    var scannedBook by mutableStateOf<BookData?>(null)
        private set

    var submitResult by mutableStateOf<String?>(null)
        private set

    var notFoundBooks = mutableStateListOf<String>()
        private set

    var unavailableBooks = mutableStateListOf<String>()
        private set


    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun checkBorrowStatusValidation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }

            when (val borrowResult = mainRepository.checkBorrowStatus()) {
                is Resource.Success -> {
                    if (!borrowResult.data.canBorrow) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = borrowResult.data.message
                            )
                        }
                        return@launch
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = borrowResult.message
                        )
                    }
                    return@launch
                }

                else -> Unit
            }

            when (val dataResult = mainRepository.validateBorrowingDate()) {
                is Resource.Success -> {
                    if (!dataResult.data.valid) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = dataResult.data.message
                            )
                        }
                        return@launch
                    } else {
                        _uiState.update { it.copy(eventId = dataResult.data.eventId) }
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = dataResult.message
                        )
                    }
                }

                else -> Unit
            }

            when (val curriculumResult = mainRepository.getCurriculumByUser()) {
                is Resource.Success -> {
                    if (curriculumResult.data.curriculum == null) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = curriculumResult.data.message
                            )
                        }
                        return@launch
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            curriculum = curriculumResult.data.curriculum,
                            isSuccess = true
                        )
                    }
                }


                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false, errorMessage = curriculumResult.message
                        )
                    }
                    return@launch
                }

                else -> Unit
            }
        }
    }

    fun clearSuccessFlag() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun fetchBook(code: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val result = mainRepository.scanBookBarcode(code)) {
                is Resource.Success -> {
                    scannedBook = result.data?.data
                }

                is Resource.Error -> {
                    errorMessage = result.message
                }

                else -> Unit
            }
            isLoading = false
        }
    }

    fun reset() {
        scannedBook = null
        errorMessage = null
    }

    fun getSchedule(onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                when (val result = mainRepository.getSchedule()) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                schedules = result.data?.data ?: emptyList()
                            )
                        }
                        onSuccess()
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                        onError(result.message)
                    }
                    else -> Unit
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
                onError(e.message ?: "Terjadi exception")
            }
        }
    }


    fun addBook(book: BookData) {
        Log.d("addBook", "Menambahkan: ${book.title}")
        if (_loanedBooks.none { it.code == book.code }) {
            _loanedBooks.add(book)
            Log.d("addBook", "Sekarang jumlah buku: ${_loanedBooks.size}")
        }
    }

    fun removeBook(code: String) {
        _loanedBooks.removeIf { it.code == code }
    }

    fun resetBook() {
        _loanedBooks.clear()
    }

    fun addToSearchHistory(query: String) {
        if (query.isNotBlank() && !_searchHistory.contains(query)) {
            _searchHistory.add(0, query) // Masukkan di awal list
        }
    }

    fun removeFromSearchHistory(query: String) {
        _searchHistory.remove(query)
    }

    fun clearSearchHistory() {
        _searchHistory.clear()
    }

    fun submitBorrowedBooks(onFinish: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    notFoundBooks = emptyList(),
                    unavailableBooks = emptyList()
                )
            }

            val bookCodes = loanedBooks.map { it.code }
            val request = BookLoanRequest(bookCodes)

            when (val result = mainRepository.submitBorrowedBook(request)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            submitMessage = result.data.message,
                            isSuccess = true
                        )
                    }
                    resetBook()
                    onFinish(true)
                }

                is Resource.Error -> {
                    // Coba parsing error
                    val fallbackMessage = result.message ?: "Terjadi kesalahan saat submit"
                    var notFound = emptyList<String>()
                    var unavailable = emptyList<String>()
                    var message = fallbackMessage

                    try {
                        val parsed = Json.decodeFromString<BorrowBooksResponse>(fallbackMessage)
                        notFound = parsed.errors?.notFoundCodes ?: emptyList()
                        unavailable = parsed.errors?.unavailableBooks ?: emptyList()
                        message = parsed.message
                    } catch (_: Exception) {}

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = message,
                            notFoundBooks = notFound,
                            unavailableBooks = unavailable
                        )
                    }

                    onFinish(false)
                }

                else -> Unit
            }
        }
    }
}

data class BorrowUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val curriculum: Curriculum? = null,
    val eventId: Int? = null,

    val schedules: List<ScheduleItem> = emptyList(),

    val submitMessage: String? = null,
    val notFoundBooks: List<String> = emptyList(),
    val unavailableBooks: List<String> = emptyList()
)