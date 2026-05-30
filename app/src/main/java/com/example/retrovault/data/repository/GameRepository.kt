package com.example.retrovault.data.repository

import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun observeGames(): Flow<List<Game>>
    fun observeGames(query: String): Flow<List<Game>>
    fun observeGame(id: Long): Flow<Game?>
    suspend fun getGameById(id: Long): Game?
    suspend fun insertGame(game: Game): Long
    suspend fun updateGame(game: Game)
    suspend fun deleteGame(game: Game)
}
