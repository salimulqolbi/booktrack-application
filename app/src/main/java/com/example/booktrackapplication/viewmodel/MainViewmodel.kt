package com.example.booktrackapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
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
import com.example.booktrack.data.response.UserResponse
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.datastore.DataStoreManager
import com.example.booktrackapplication.data.response.ActivityItem
import com.example.booktrackapplication.data.response.BookLoanRequest
import com.example.booktrackapplication.data.response.BookReturnRequest
import com.example.booktrackapplication.data.response.BorrowBooksResponse
import com.example.booktrackapplication.data.response.HistoryItem
import com.example.booktrackapplication.data.response.ProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class MainViewmodel(
    private val mainRepository: MainRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(BorrowUiState())
    val uiState: StateFlow<BorrowUiState> = _uiState.asStateFlow()

    private val _returnUiState = MutableStateFlow(ReturnUiState())
    val returnUiState: StateFlow<ReturnUiState> = _returnUiState.asStateFlow()

    private val _loanedBooks = mutableStateListOf<BookData>()
    val loanedBooks: List<BookData> = _loanedBooks

    private val _searchHistory = mutableStateListOf<String>()
    val searchHistory: List<String> get() = _searchHistory

    private val _errorMessageState = mutableStateOf<String?>(null)
    val errorMessageState: State<String?> = _errorMessageState

//    private val _activityState = mutableStateListOf<ActivityItem>()
//    val activityState: List<ActivityItem> = _activityState

    private val _activityUiState = MutableStateFlow(ActivityUiState())
    val activityUiState: StateFlow<ActivityUiState> = _activityUiState.asStateFlow()

    private val _historyUiState = MutableStateFlow(HistoryUiState())
    val historyUiState: StateFlow<HistoryUiState> = _historyUiState.asStateFlow()

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()

    private val _searchBook = MutableStateFlow<BookData?>(null)
    val searchBook = _searchBook.asStateFlow()

    var scannedBook by mutableStateOf<BookData?>(null)
        private set

    var searchedBook by mutableStateOf<BookData?>(null)
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
                        _uiState.update {
                            it.copy(
                                eventId = dataResult.data.eventId
                            )
                        }
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
        _returnUiState.update { it.copy(isSuccess = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
        _returnUiState.update { it.copy(errorMessage = null) }
    }

    fun fetchBook(code: String) {
        Log.d("BOOK_DEBUG", "fetchBook() called with: $code")
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

    fun searchBook(code: String) {
        Log.d("BOOK_DEBUG", "fetchBook() called with: $code")
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                when (val result = mainRepository.scanBookBarcode(code)) {
                    is Resource.Success -> {
                        _searchBook.value = result.data?.data

                        Log.d("BOOK_DEBUG", "fetchBook() called with: ${_searchBook.value}")
                    }

                    is Resource.Error -> {
                        errorMessage = result.message
                    }

                    else -> Unit
                }
                isLoading = false
            } catch (e: Exception) {
                // Handle error jika ada masalah dengan API request
                _searchBook.value = null
            }
        }
    }

    fun resetSearchResult() {
        _searchBook.value = null
    }


    fun fetchReturnBook(code: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null // Reset error message
            when (val result = mainRepository.scanBookBarcode(code)) {
                is Resource.Success -> {
                    scannedBook = result.data?.data
                    val borrowedId = scannedBook?.borrowedBy?.name

                    if (borrowedId != dataStoreManager.getUser()?.name) {
                        _errorMessageState.value = "Scan Dibatalkan. Buku ini bukan milik Anda."
                        scannedBook = null  // Clear scannedBook if the user doesn't match
                    } else {
                        // Buku valid dan sesuai
                        scannedBook = scannedBook
                    }
                }

                is Resource.Error -> {
                    errorMessage = result.message
                    scannedBook = null  // Clear scannedBook if there's an error
                }

                else -> Unit
            }
            isLoading = false
        }
    }


    fun clearErrorMessage() {
        _errorMessageState.value = null
    }

    fun resetReturnBook() {
        scannedBook = null
        _loanedBooks.clear()
        _errorMessageState.value = null
        errorMessage = null
    }

    fun reset() {
        scannedBook = null
        _errorMessageState.value = null
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
            _searchHistory.add(0, query)
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

data class ActivityUiState(
    val isLoading: Boolean = false,
    val activities: List<ActivityItem> = emptyList(),
    val errorMessage: String? = null,
    val emptyMessage: String? = null
)

data class HistoryUiState(
    val isLoading: Boolean = false,
    val history: Map<String, List<HistoryItem>> = emptyMap(),
    val errorMessage: String? = null,
    val emptyMessage: String? = null
)

data class ReturnUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val eventId: Int? = null,
    val curriculum: Curriculum? = null,
    val submitMessage: String? = null,
    val notFoundBooks: List<String> = emptyList(),
    val unavailableBooks: List<String> = emptyList()
)

data class UserUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: ProfileResponse? = null
)