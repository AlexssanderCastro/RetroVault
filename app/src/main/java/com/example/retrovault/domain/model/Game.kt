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
    val imageUri: String? = null,
    val createdAt: Long,
    val favorito: Boolean = false,
    val zerado: Boolean = false,
    val naListaDeDesejos: Boolean = false,
    val dataConclusao: Long? = null,
    val horasJogadas: Int? = null
)
