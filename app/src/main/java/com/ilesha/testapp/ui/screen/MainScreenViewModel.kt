package com.ilesha.testapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilesha.testapp.domain.model.Entity
import com.ilesha.testapp.domain.model.Result
import com.ilesha.testapp.domain.usecase.GetAllIdsUseCase
import com.ilesha.testapp.domain.usecase.GetItemByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getAllIdsUseCase: GetAllIdsUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private var currentIndex = 0
    private var idList = emptyList<Int>()

    init {
        loadIdList()
    }

    fun loadIdList() {
        _uiState.update { MainScreenUiState.Loading }
        viewModelScope.launch {
            val result = getAllIdsUseCase()
            when (result) {
                is Result.Error -> {
                    _uiState.update { MainScreenUiState.Error(result.exception.message ?: "") }
                }
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        _uiState.update { MainScreenUiState.Error("no elements found") }
                    } else {
                        idList = result.data
                        getCurrentItem()
                    }
                }
            }
        }
    }

    fun getCurrentItem() {
        _uiState.update { MainScreenUiState.Loading }
        viewModelScope.launch {
            if (idList.isEmpty()) {
                loadIdList()
                return@launch
            }
            val id = idList[currentIndex]
            val result = getItemByIdUseCase(id)
            when (result) {
                is Result.Error -> {
                    _uiState.update { MainScreenUiState.Error(result.exception.message ?: "") }
                }
                is Result.Success -> {
                    if (result.data is Entity.Game) {
                        onNextClicked()
                        return@launch
                    }
                    _uiState.update { MainScreenUiState.Content(
                        currentId = id,
                        currentEntity = result.data
                    ) }
                    println()
                }
            }
        }
    }

    fun onNextClicked() {
        if (idList.isEmpty()) return
        currentIndex = (currentIndex + 1) % idList.size
        getCurrentItem()
    }

    fun onRefreshClicked() {
        getCurrentItem()
    }

}