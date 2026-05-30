package com.example.retrovault.data.repository

import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGameRepository : GameRepository {
    private val games = listOf(
        Game(1, "Chrono Trigger", "SNES", 1995, 4.8f, ""),
        Game(2, "Castlevania: Symphony", "PS1", 1997, 4.6f, ""),
        Game(3, "Mega Man X", "SNES", 1993, 4.4f, ""),
        Game(4, "Sonic the Hedgehog 2", "Genesis", 1992, 4.2f, ""),
        Game(5, "The Legend of Zelda", "NES", 1986, 4.7f, "")
    )

    override fun observeGames(): Flow<List<Game>> = flowOf(games)

    override suspend fun getGameById(id: Long): Game? = games.firstOrNull { it.id == id }
}
