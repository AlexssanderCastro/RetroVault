package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrovault.data.repository.GameRepository
import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: GameRepository,
    private val gameId: Long
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<Game>>(UiState.Loading)
    val state: StateFlow<UiState<Game>> = _state.asStateFlow()

    private val _events = MutableSharedFlow<FormEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<FormEvent> = _events

    init {
        observeGame()
    }

    private fun observeGame() {
        viewModelScope.launch {
            repository.observeGame(gameId)
                .onStart { _state.value = UiState.Loading }
                .catch { _state.value = UiState.Error("Falha ao carregar detalhes") }
                .collect { game ->
                    _state.value = when (game) {
                        null -> UiState.Error("Jogo nao encontrado")
                        else -> UiState.Success(game)
                    }
                }
        }
    }

    fun deleteGame() {
        viewModelScope.launch {
            val current = (_state.value as? UiState.Success)?.data
            if (current == null) {
                _events.emit(FormEvent.ShowMessage("Jogo nao encontrado"))
                return@launch
            }
            repository.deleteGame(current)
            _events.emit(FormEvent.ShowMessage("Jogo removido"))
            _events.emit(FormEvent.NavigateBack)
        }
    }
}
