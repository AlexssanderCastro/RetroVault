package com.example.retrovault.utils

import android.content.Context
import com.example.retrovault.data.repository.FakeGameRepository
import com.example.retrovault.data.repository.GameRepository

interface AppContainer {
    val gameRepository: GameRepository
}

class AppContainerImpl(context: Context) : AppContainer {
    override val gameRepository: GameRepository = FakeGameRepository()
}
