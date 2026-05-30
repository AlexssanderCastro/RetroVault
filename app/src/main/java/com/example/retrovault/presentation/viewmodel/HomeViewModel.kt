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

data class HomeStats(
    val totalGames: Int = 0,
    val totalFavoritos: Int = 0,
    val totalZerados: Int = 0,
    val totalDesejados: Int = 0,
    val averageRating: Float = 0f,
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

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val repository: GameRepository
) : ViewModel() {
    private val query = MutableStateFlow("")

    val state: StateFlow<HomeUiState> = query
        .flatMapLatest { search ->
            repository.observeGames()
                .map { games ->
                    val filtered = filterGames(games, search)
                    val stats = buildStats(games)
                    HomeUiState(
                        isLoading = false,
                        games = filtered,
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

    fun toggleFavorite(gameId: Long) {
        viewModelScope.launch {
            repository.toggleFavorite(gameId)
        }
    }

    fun toggleWishlist(gameId: Long) {
        viewModelScope.launch {
            repository.toggleWishlist(gameId)
        }
    }

    fun markCompleted(gameId: Long, completed: Boolean) {
        viewModelScope.launch {
            val date = if (completed) System.currentTimeMillis() else null
            repository.markCompleted(gameId, completed, date)
        }
    }

    private fun filterGames(games: List<Game>, query: String): List<Game> {
        val normalizedQuery = query.trim()
        return games
            .asSequence()
            .filter { game ->
                normalizedQuery.isBlank() ||
                    game.name.contains(normalizedQuery, ignoreCase = true) ||
                    game.platform.contains(normalizedQuery, ignoreCase = true) ||
                    game.genre.contains(normalizedQuery, ignoreCase = true)
            }
            .toList()
    }

    private fun buildStats(games: List<Game>): HomeStats {
        val platformCounts = games
            .groupBy { it.platform }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }
        val latest = games.sortedByDescending { it.createdAt }.take(3)
        val totalFavoritos = games.count { it.favorito }
        val totalZerados = games.count { it.zerado }
        val totalDesejados = games.count { it.naListaDeDesejos }
        val avgRating = if (games.isEmpty()) 0f else games.map { it.rating }.average().toFloat()
        return HomeStats(
            totalGames = games.size,
            totalFavoritos = totalFavoritos,
            totalZerados = totalZerados,
            totalDesejados = totalDesejados,
            averageRating = avgRating,
            platformCounts = platformCounts,
            latestGames = latest
        )
    }
}
