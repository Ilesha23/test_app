package com.ilesha.testapp.ui.screen

import com.ilesha.testapp.domain.model.Entity

sealed class MainScreenUiState {

    object Initial : MainScreenUiState()

    object Loading : MainScreenUiState()

    data class Error(
        val message: String
    ) : MainScreenUiState()

    data class Content(
        val currentEntity: Entity,
        val currentId: Int
    ) : MainScreenUiState()

}