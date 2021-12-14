package com.straxfromibr.jetpackghapi.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straxfromibr.jetpackghapi.model.repository.User
import com.straxfromibr.jetpackghapi.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel()
{
    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        data class Success(val user: User) : UiState()
        object Failure : UiState()
    }

    // Viewの状態
    val uiState: MutableState<UiState> = mutableStateOf(UiState.Initial)

    // 検索フォームの入力文字
    val searchQuery: MutableState<String> = mutableStateOf("")

    // 検索の実行
    // searchQueryをRepository経由
    fun onSearchTapped() {
        val searchQuery: String = searchQuery.value

        viewModelScope.launch {
            uiState.value = UiState.Loading
            runCatching {
                userRepository.getUser(userName = searchQuery)
            }.onSuccess {
                uiState.value = UiState.Success(user = it)
            }.onFailure {
                uiState.value = UiState.Failure
            }
        }
    }
}