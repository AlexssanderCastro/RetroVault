package com.example.retrovault.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object AddGame : Screen("add")
    data object EditGame : Screen("edit/{gameId}") {
        fun createRoute(gameId: Long) = "edit/$gameId"
    }
    data object Details : Screen("details/{gameId}") {
        fun createRoute(gameId: Long) = "details/$gameId"
    }
    data object Favorites : Screen("favorites")
}
