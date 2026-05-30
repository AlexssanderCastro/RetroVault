package com.example.retrovault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.retrovault.RetroVaultApp
import com.example.retrovault.data.repository.GameRepository

class RetroVaultViewModelFactory(
    private val repository: GameRepository,
    private val gameId: Long? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository) as T
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(repository) as T
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                val resolvedId = gameId ?: 0L
                DetailsViewModel(repository, resolvedId) as T
            }
            modelClass.isAssignableFrom(AddGameViewModel::class.java) ->
                AddGameViewModel(repository, gameId) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        fun fromApplication(app: RetroVaultApp, gameId: Long? = null): RetroVaultViewModelFactory {
            return RetroVaultViewModelFactory(app.container.gameRepository, gameId)
        }
    }
}
