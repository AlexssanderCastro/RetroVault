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

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val games: List<Game> = emptyList(),
    val errorMessage: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModel(
    private val repository: GameRepository
) : ViewModel() {
    private val refresh = MutableStateFlow(Unit)

    val state: StateFlow<FavoritesUiState> = refresh
        .flatMapLatest {
            repository.observeFavorites()
                .map { games -> FavoritesUiState(isLoading = false, games = games) }
                .onStart { emit(FavoritesUiState(isLoading = true)) }
                .catch { emit(FavoritesUiState(isLoading = false, errorMessage = "Falha ao carregar favoritos")) }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), FavoritesUiState())

    fun toggleFavorite(gameId: Long) {
        viewModelScope.launch {
            repository.toggleFavorite(gameId)
        }
    }
}
