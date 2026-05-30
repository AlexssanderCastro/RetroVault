package com.example.retrovault.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val platform: String,
    val year: Int,
    val rating: Float,
    val coverUrl: String
)

