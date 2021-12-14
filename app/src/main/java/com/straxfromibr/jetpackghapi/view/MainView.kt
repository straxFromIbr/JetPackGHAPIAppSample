package com.straxfromibr.jetpackghapi.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.straxfromibr.jetpackghapi.model.repository.User
import com.straxfromibr.jetpackghapi.viewmodel.MainViewModel

@Composable
fun MainView(mainViewModel: MainViewModel) {
    val uiState: MainViewModel.UiState by mainViewModel.uiState

    Column(Modifier.fillMaxWidth()){
        SearchView(
            searchQuery = mainViewModel.searchQuery,
            onSearchButtonTapped = mainViewModel::onSearchTapped,
        )
        when (uiState) {
            is MainViewModel.UiState.Initial -> {
                InitialView()
            }
            is MainViewModel.UiState.Loading -> {
                LoadingView()
            }
            is MainViewModel.UiState.Success -> {
                UserDetailView(user = uiState.requireUser())
            }
            is MainViewModel.UiState.Failure -> {
                ErrorView()
            }
        }
    }
}

private fun MainViewModel.UiState.requireUser(): User {
    if (this !is MainViewModel.UiState.Success) {
        throw IllegalStateException("user is not loaded")
    }
    return user
}