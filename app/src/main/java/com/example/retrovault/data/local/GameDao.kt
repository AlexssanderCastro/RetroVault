package com.example.retrovault.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM games ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE favorito = 1 ORDER BY createdAt DESC")
    fun observeFavorites(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE naListaDeDesejos = 1 ORDER BY createdAt DESC")
    fun observeWishlist(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE zerado = 1 ORDER BY createdAt DESC")
    fun observeCompleted(): Flow<List<GameEntity>>

    @Query(
        "SELECT * FROM games " +
            "WHERE name LIKE '%' || :query || '%' " +
            "OR platform LIKE '%' || :query || '%' " +
            "ORDER BY createdAt DESC"
    )
    fun observeByQuery(query: String): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE id = :id")
    fun observeById(id: Long): Flow<GameEntity?>

    @Query("SELECT * FROM games WHERE id = :id")
    suspend fun getById(id: Long): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity): Long

    @Update
    suspend fun update(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)

    @Query("SELECT COUNT(*) FROM games")
    fun observeTotalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM games WHERE favorito = 1")
    fun observeFavoritesCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM games WHERE zerado = 1")
    fun observeCompletedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM games WHERE naListaDeDesejos = 1")
    fun observeWishlistCount(): Flow<Int>

    @Query("SELECT AVG(rating) FROM games")
    fun observeAverageRating(): Flow<Float?>
}
