package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrovault.data.repository.GameRepository
import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

data class HomeStats(
    val totalGames: Int = 0,
    val platformCounts: List<Pair<String, Int>> = emptyList(),
    val latestGames: List<Game> = emptyList()
)

data class HomeUiState(
    val isLoading: Boolean = true,
    val games: List<Game> = emptyList(),
    val query: String = "",
    val stats: HomeStats = HomeStats(),
    val errorMessage: String? = null
)

class HomeViewModel(
    private val repository: GameRepository
) : ViewModel() {
    private val query = MutableStateFlow("")

    val state: StateFlow<HomeUiState> = query
        .flatMapLatest { search ->
            repository.observeGames(search)
                .map { games ->
                    val stats = buildStats(games)
                    HomeUiState(
                        isLoading = false,
                        games = games,
                        query = search,
                        stats = stats,
                        errorMessage = null
                    )
                }
                .onStart { emit(HomeUiState(isLoading = true, query = search)) }
                .catch { emit(HomeUiState(isLoading = false, query = search, errorMessage = "Falha ao carregar jogos")) }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), HomeUiState())

    fun updateQuery(value: String) {
        query.value = value
    }

    private fun buildStats(games: List<Game>): HomeStats {
        val platformCounts = games
            .groupBy { it.platform }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }
        val latest = games.sortedByDescending { it.createdAt }.take(3)
        return HomeStats(
            totalGames = games.size,
            platformCounts = platformCounts,
            latestGames = latest
        )
    }
}
