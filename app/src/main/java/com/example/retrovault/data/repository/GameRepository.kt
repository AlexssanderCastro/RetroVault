package com.example.retrovault.data.repository

import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun observeGames(): Flow<List<Game>>
    suspend fun getGameById(id: Long): Game?
}

