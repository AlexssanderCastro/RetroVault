package com.example.retrovault.data.repository

import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGameRepository : GameRepository {
    private val games = listOf(
        Game(id = 1, name = "Chrono Trigger", platform = "SNES", year = 1995, genre = "RPG", developer = "Square", rating = 4.8f, notes = "", createdAt = 0L),
        Game(id = 2, name = "Castlevania: Symphony", platform = "PS1", year = 1997, genre = "Action", developer = "Konami", rating = 4.6f, notes = "", createdAt = 0L),
        Game(id = 3, name = "Mega Man X", platform = "SNES", year = 1993, genre = "Action", developer = "Capcom", rating = 4.4f, notes = "", createdAt = 0L),
        Game(id = 4, name = "Sonic the Hedgehog 2", platform = "Genesis", year = 1992, genre = "Platform", developer = "Sega", rating = 4.2f, notes = "", createdAt = 0L),
        Game(id = 5, name = "The Legend of Zelda", platform = "NES", year = 1986, genre = "Adventure", developer = "Nintendo", rating = 4.7f, notes = "", createdAt = 0L)
    )

    override fun observeGames(): Flow<List<Game>> = flowOf(games)

    override fun observeGames(query: String): Flow<List<Game>> = flowOf(
        games.filter { it.name.contains(query, true) || it.platform.contains(query, true) }
    )

    override fun observeGame(id: Long): Flow<Game?> = flowOf(games.firstOrNull { it.id == id })

    override suspend fun getGameById(id: Long): Game? = games.firstOrNull { it.id == id }

    override suspend fun insertGame(game: Game): Long = game.id

    override suspend fun updateGame(game: Game) = Unit

    override suspend fun deleteGame(game: Game) = Unit

    // Fake implementations for new features (no state mutation, returns static data)
    override fun observeFavorites(): Flow<List<Game>> = flowOf(games.filter { it.favorito })

    override fun observeWishlist(): Flow<List<Game>> = flowOf(games.filter { it.naListaDeDesejos })

    override fun observeCompleted(): Flow<List<Game>> = flowOf(games.filter { it.zerado })

    override suspend fun toggleFavorite(gameId: Long) {
        // No-op in fake repository
    }

    override suspend fun toggleWishlist(gameId: Long) {
        // No-op in fake repository
    }

    override suspend fun markCompleted(gameId: Long, completed: Boolean, dateConclusao: Long?) {
        // No-op in fake repository
    }
}
