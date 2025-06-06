package com.example.booktrackapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktrack.data.repository.main.MainRepository
import com.example.booktrack.data.response.BookData
import com.example.booktrack.data.response.BorrowStatusResponse
import com.example.booktrack.data.response.Curriculum
import com.example.booktrack.data.response.ScheduleItem
import com.example.booktrack.utils.Resource
import com.example.booktrackapplication.data.datastore.DataStoreManager
import com.example.booktrackapplication.data.response.ActivityItem
import com.example.booktrackapplication.data.response.BookLoanRequest
import com.example.booktrackapplication.data.response.BookReturnRequest
import com.example.booktrackapplication.data.response.HistoryGroup
import com.example.booktrackapplication.data.response.ProfileResponse
import com.example.booktrackapplication.data.response.ReturnBooksResponse
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

    private val _returnLoanedBooks = mutableStateListOf<BookData>()
    val returnLoanedBooks: List<BookData> = _returnLoanedBooks

    private val _searchHistory = mutableStateListOf<String>()
    val searchHistory: List<String> get() = _searchHistory

    private val _errorMessageState = mutableStateOf<String?>(null)
    val errorMessageState: State<String?> = _errorMessageState

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

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun checkBorrowStatusValidation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }

            when (val borrowResult = mainRepository.checkBorrowStatus()) {
                is Resource.Success -> {
                    val borrowData = borrowResult.data

                    if (!borrowData.canBorrow) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = borrowData.message,
                                currentBorrowed = borrowData
                            )
                        }
                        return@launch
                    }
                    _uiState.update {
                        it.copy(currentBorrowed = borrowData)
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
        _uiState.update {
            it.copy(
                isSuccess = false,
                submitMessage = null
            )
        }
        _returnUiState.update {
            it.copy(
                isSuccess = false,
                submitMessage = null
            )
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(
                errorMessage = null,
                unavailableBooks = emptyList()
            )
        }
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
                _searchBook.value = null
            }
        }
    }

    fun fetchReturnBook(code: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val result = mainRepository.scanBookBarcode(code)) {
                is Resource.Success -> {
                    scannedBook = result.data?.data
                    val borrowedId = scannedBook?.borrowedBy?.name

                    if (borrowedId != dataStoreManager.getUser()?.name) {
                        _errorMessageState.value = "Scan Dibatalkan. Buku ini bukan milik Anda."
                        scannedBook = null
                    } else {
                        scannedBook = scannedBook
                    }
                }

                is Resource.Error -> {
                    errorMessage = result.message
                    scannedBook = null
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
        _returnLoanedBooks.clear()
        _errorMessageState.value = null
        errorMessage = null
    }

    fun reset() {
        scannedBook = null
        _errorMessageState.value = null
        errorMessage = null
    }

    fun addBook(book: BookData, isSubmitted: Boolean = false) {
        if (_loanedBooks.none { it.code == book.code }) {
            _loanedBooks.add(book.copy(isSubmitted = isSubmitted))
        }
    }

    fun addBookReturn(book: BookData) {
        if (_returnLoanedBooks.none { it.code == book.code }) {
            _returnLoanedBooks.add(book)
        }
    }

    fun removeBook(code: String) {
        _loanedBooks.removeIf { it.code == code && !it.isSubmitted }
    }

    fun removeBookReturn(code: String) {
        _returnLoanedBooks.removeIf { it.code == code }
    }

    fun resetBook() {
        _loanedBooks.clear()
    }

    fun resetBookReturn() {
        _returnLoanedBooks.clear()
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
            val bookCodes = loanedBooks
                .filter { !it.isSubmitted }
                .map { it.code }
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
                    _loanedBooks.replaceAll { book ->
                        if (!book.isSubmitted) book.copy(isSubmitted = true) else book
                    }
                    resetBook()
                    onFinish(true)
                }

                is Resource.Error -> {

                    var notFound = emptyList<String>()
                    var unavailable = emptyList<String>()
                    var message = result.message ?: "Terjadi kesalahan saat pengembalian"

                    result.data?.let { response ->
                        notFound = response.errors?.notFoundCodes ?: emptyList()
                        unavailable = response.errors?.unavailableBooks ?: emptyList()
                        message = response.message
                    }

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


    fun submitReturnedBooks(onFinish: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            _returnUiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    notFoundBooks = emptyList(),
                    unavailableBooks = emptyList()
                )
            }

            val request = BookReturnRequest(bookCodes = returnLoanedBooks.map { it.code })

            when (val result = mainRepository.submitReturnedBook(request)) {
                is Resource.Success -> {
                    _returnUiState.update {
                        it.copy(
                            isLoading = false,
                            submitMessage = result.data.message,
                            isSuccess = true
                        )
                    }
//                    resetBook()
                    resetBookReturn()
                    onFinish(true)
                }

                is Resource.Error -> {
                    val fallbackMessage = result.message ?: "Terjadi kesalahan saat pengembalian"
                    var notFound = emptyList<String>()
                    var unavailable = emptyList<String>()
                    var message = fallbackMessage

                    try {
                        val parsed = Json.decodeFromString<ReturnBooksResponse>(fallbackMessage)
                        notFound = parsed.errors?.notFoundCodes ?: emptyList()
                        unavailable = parsed.errors?.unavailableBooks ?: emptyList()
                        message = parsed.message
                    } catch (e: Exception) {}

                    _returnUiState.update {
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

    fun clearSubmitMessage() {
        _returnUiState.update {
            it.copy(submitMessage = null)
        }
    }

    fun getActivity() {
        viewModelScope.launch {
            _activityUiState.update { it.copy(isLoading = true, errorMessage = null, emptyMessage = null) }

            when (val result = mainRepository.getActivity()) {
                is Resource.Success -> {
                    val data = result.data?.data ?: emptyList()

                    _activityUiState.update {
                        it.copy(
                            isLoading = false,
                            activities = data,
                            errorMessage = null,
                            emptyMessage = if (data.isEmpty()) "Tidak ada aktivitas ditemukan" else null
                        )
                    }
                }
                is Resource.Error -> {
                    _activityUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Terjadi kesalahan",
                            activities = emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            _historyUiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    emptyMessage = null
                )
            }

            when (val result = mainRepository.getHistory()) {
                is Resource.Success -> {
                    val historyItems: List<HistoryGroup> = result.data.data.orEmpty()

                    _historyUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            history = historyItems,
                            emptyMessage = if (historyItems.isEmpty()) "Belum ada Riwayat" else null
                        )
                    }
                }

                is Resource.Error -> {
                    _historyUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Gagal memuat riwayat"
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _userUiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = mainRepository.getUser()) {
                is Resource.Success -> {
                    _userUiState.update {
                        it.copy(
                            isLoading = false,
                            user = result.data.data,
                            errorMessage = null
                        )
                    }
                }
                is Resource.Error -> {
                    _userUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            user = null
                        )
                    }
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
    val currentBorrowed: BorrowStatusResponse? = null,
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
    val history: List<HistoryGroup> = emptyList(),
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