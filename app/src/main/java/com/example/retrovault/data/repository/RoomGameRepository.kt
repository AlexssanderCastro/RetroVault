package com.example.retrovault.data.repository

import com.example.retrovault.data.local.GameDao
import com.example.retrovault.data.mappers.toDomain
import com.example.retrovault.data.mappers.toEntity
import com.example.retrovault.domain.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomGameRepository(
    private val gameDao: GameDao
) : GameRepository {
    override fun observeGames(): Flow<List<Game>> =
        gameDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override fun observeGames(query: String): Flow<List<Game>> =
        if (query.isBlank()) {
            observeGames()
        } else {
            gameDao.observeByQuery(query).map { entities -> entities.map { it.toDomain() } }
        }

    override fun observeGame(id: Long): Flow<Game?> =
        gameDao.observeById(id).map { it?.toDomain() }

    override suspend fun getGameById(id: Long): Game? =
        gameDao.getById(id)?.toDomain()

    override suspend fun insertGame(game: Game): Long =
        gameDao.insert(game.toEntity())

    override suspend fun updateGame(game: Game) {
        gameDao.update(game.toEntity())
    }

    override suspend fun deleteGame(game: Game) {
        gameDao.delete(game.toEntity())
    }
}

