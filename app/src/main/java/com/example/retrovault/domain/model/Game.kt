package com.example.retrovault.domain.model

data class Game(
    val id: Long,
    val name: String,
    val platform: String,
    val year: Int,
    val genre: String,
    val developer: String,
    val rating: Float,
    val notes: String,
    val createdAt: Long
)
