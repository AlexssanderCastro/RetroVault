package com.example.retrovault.domain.model

data class Game(
    val id: Long,
    val name: String,
    val platform: String,
    val year: Int,
    val rating: Float,
    val coverUrl: String
)

