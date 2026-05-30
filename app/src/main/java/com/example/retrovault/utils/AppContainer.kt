package com.example.retrovault.utils

import android.content.Context
import com.example.retrovault.data.local.RetroVaultDatabase
import com.example.retrovault.data.repository.GameRepository
import com.example.retrovault.data.repository.RoomGameRepository

interface AppContainer {
    val gameRepository: GameRepository
}

class AppContainerImpl(context: Context) : AppContainer {
    private val database = RetroVaultDatabase.getInstance(context)

    override val gameRepository: GameRepository = RoomGameRepository(database.gameDao())
}
