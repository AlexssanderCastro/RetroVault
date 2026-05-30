package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrovault.data.repository.GameRepository
import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<Game>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Game>>> = _state.asStateFlow()

    init {
        observeGames()
    }

    private fun observeGames() {
        viewModelScope.launch {
            repository.observeGames()
                .onStart { _state.value = UiState.Loading }
                .catch { _state.value = UiState.Error("Falha ao carregar jogos") }
                .collect { games ->
                    _state.value = if (games.isEmpty()) UiState.Empty else UiState.Success(games)
                }
        }
    }
}

