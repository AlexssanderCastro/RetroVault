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

    override fun observeFavorites(): Flow<List<Game>> =
        gameDao.observeFavorites().map { entities -> entities.map { it.toDomain() } }

    override fun observeWishlist(): Flow<List<Game>> =
        gameDao.observeWishlist().map { entities -> entities.map { it.toDomain() } }

    override fun observeCompleted(): Flow<List<Game>> =
        gameDao.observeCompleted().map { entities -> entities.map { it.toDomain() } }

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

    override suspend fun toggleFavorite(gameId: Long) {
        val entity = gameDao.getById(gameId) ?: return
        val updated = entity.copy(favorito = !entity.favorito)
        gameDao.update(updated)
    }

    override suspend fun toggleWishlist(gameId: Long) {
        val entity = gameDao.getById(gameId) ?: return
        val updated = entity.copy(naListaDeDesejos = !entity.naListaDeDesejos)
        gameDao.update(updated)
    }

    override suspend fun markCompleted(gameId: Long, completed: Boolean, dateConclusao: Long?) {
        val entity = gameDao.getById(gameId) ?: return
        val updated = entity.copy(zerado = completed, dataConclusao = dateConclusao)
        gameDao.update(updated)
    }
}

