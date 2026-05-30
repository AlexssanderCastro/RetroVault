package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrovault.data.repository.GameRepository
import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: GameRepository,
    private val gameId: Long
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<Game>>(UiState.Loading)
    val state: StateFlow<UiState<Game>> = _state.asStateFlow()

    init {
        loadGame()
    }

    private fun loadGame() {
        viewModelScope.launch {
            val game = repository.getGameById(gameId)
            _state.value = when {
                game == null -> UiState.Error("Jogo nao encontrado")
                else -> UiState.Success(game)
            }
        }
    }
}


