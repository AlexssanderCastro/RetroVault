package com.example.retrovault.data.repository

import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGameRepository : GameRepository {
    private val games = listOf(
        Game(1, "Chrono Trigger", "SNES", 1995, "RPG", "Square", 4.8f, "", 0L),
        Game(2, "Castlevania: Symphony", "PS1", 1997, "Action", "Konami", 4.6f, "", 0L),
        Game(3, "Mega Man X", "SNES", 1993, "Action", "Capcom", 4.4f, "", 0L),
        Game(4, "Sonic the Hedgehog 2", "Genesis", 1992, "Platform", "Sega", 4.2f, "", 0L),
        Game(5, "The Legend of Zelda", "NES", 1986, "Adventure", "Nintendo", 4.7f, "", 0L)
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
}
