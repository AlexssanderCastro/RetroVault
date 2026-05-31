package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrovault.data.repository.GameRepository
import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CompletedUiState(
    val isLoading: Boolean = true,
    val games: List<Game> = emptyList(),
    val errorMessage: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
class CompletedViewModel(private val repository: GameRepository) : ViewModel() {
    private val refresh = MutableStateFlow(Unit)

    val state: StateFlow<CompletedUiState> = refresh
        .flatMapLatest {
            repository.observeCompleted()
                .map { games -> CompletedUiState(isLoading = false, games = games) }
                .onStart { emit(CompletedUiState(isLoading = true)) }
                .catch { emit(CompletedUiState(isLoading = false, errorMessage = "Falha ao carregar zerados")) }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), CompletedUiState())

    fun unmarkCompleted(gameId: Long) {
        viewModelScope.launch {
            // unmark as completed
            repository.markCompleted(gameId, false, null)
        }
    }
}
