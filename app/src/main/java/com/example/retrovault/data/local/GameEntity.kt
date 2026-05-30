package com.example.retrovault.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val platform: String,
    val year: Int,
    val genre: String,
    val developer: String,
    val rating: Float,
    val notes: String,
    val createdAt: Long
)
