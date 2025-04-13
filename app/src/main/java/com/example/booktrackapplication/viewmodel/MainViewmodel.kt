package com.example.booktrackapplication.viewmodel

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewmodel(
    private val mainRepository: MainRepository,
    private val authRepository: AuthRepository
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(BorrowUiState())
    val uiState: StateFlow<BorrowUiState> = _uiState.asStateFlow()

    private val _loanedBooks = mutableStateListOf<BookData>()
    val loanedBooks: List<BookData> = _loanedBooks

    var scannedBook by mutableStateOf<BookData?>(null)
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

    // SCAN FEAT
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

    fun getSchedule(onComplete: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = mainRepository.getSchedule()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            schedules = result.data?.data ?: emptyList()
                        )
                    }
                    onComplete()
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                    onComplete()
                }

                else -> Unit
            }
        }
    }

    fun addBook(book: BookData) {
        if (_loanedBooks.none { it.code == book.code }) {
            _loanedBooks.add(book)
        }
    }

    fun removeBook(code: String) {
        _loanedBooks.removeIf { it.code == code }
    }

    fun resetBook() {
        _loanedBooks.clear()
    }

}

data class BorrowUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val curriculum: Curriculum? = null,
    val eventId: Int? = null,

    val schedules: List<ScheduleItem> = emptyList()
)
