package com.example.retrovault.data.mappers

import com.example.retrovault.data.local.GameEntity
import com.example.retrovault.domain.model.Game

fun GameEntity.toDomain(): Game =
    Game(
        id = id,
        name = name,
        platform = platform,
        year = year,
        genre = genre,
        developer = developer,
        rating = rating,
        notes = notes,
        imageUri = imageUri,
        createdAt = createdAt,
        favorito = favorito,
        zerado = zerado,
        naListaDeDesejos = naListaDeDesejos,
        dataConclusao = dataConclusao,
        horasJogadas = horasJogadas
    )

fun Game.toEntity(): GameEntity =
    GameEntity(
        id = id,
        name = name,
        platform = platform,
        year = year,
        genre = genre,
        developer = developer,
        rating = rating,
        notes = notes,
        imageUri = imageUri,
        createdAt = createdAt,
        favorito = favorito,
        zerado = zerado,
        naListaDeDesejos = naListaDeDesejos,
        dataConclusao = dataConclusao,
        horasJogadas = horasJogadas
    )
