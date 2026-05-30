package com.example.retrovault.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrovault.RetroVaultApp
import com.example.retrovault.presentation.screens.AddGameScreen
import com.example.retrovault.presentation.screens.DetailsScreen
import com.example.retrovault.presentation.screens.FavoritesScreen
import com.example.retrovault.presentation.screens.HomeScreen
import com.example.retrovault.presentation.screens.SplashScreen
import com.example.retrovault.presentation.viewmodel.AddGameViewModel
import com.example.retrovault.presentation.viewmodel.DetailsViewModel
import com.example.retrovault.presentation.viewmodel.FavoritesViewModel
import com.example.retrovault.presentation.viewmodel.HomeViewModel
import com.example.retrovault.presentation.viewmodel.RetroVaultViewModelFactory

@Composable
fun RetroVaultNavHost() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as RetroVaultApp

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route) {
            val factory = remember { RetroVaultViewModelFactory.fromApplication(app) }
            val viewModel: HomeViewModel = viewModel(factory = factory)
            HomeScreen(
                viewModel = viewModel,
                onAddGame = { navController.navigate(Screen.AddGame.route) },
                onGameSelected = { gameId ->
                    navController.navigate(Screen.Details.createRoute(gameId))
                },
                onFavorites = { navController.navigate(Screen.Favorites.route) }
            )
        }
        composable(Screen.Favorites.route) {
            val factory = remember { RetroVaultViewModelFactory.fromApplication(app) }
            val viewModel: FavoritesViewModel = viewModel(factory = factory)
            FavoritesScreen(
                viewModel = viewModel,
                onHome = { navController.popBackStack(Screen.Home.route, false) },
                onGameSelected = { gameId -> navController.navigate(Screen.Details.createRoute(gameId)) },
                onToggleFavorite = viewModel::toggleFavorite
            )
        }
        composable(Screen.AddGame.route) {
            val factory = remember { RetroVaultViewModelFactory.fromApplication(app) }
            val viewModel: AddGameViewModel = viewModel(factory = factory)
            AddGameScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.EditGame.route,
            arguments = listOf(navArgument("gameId") { type = NavType.LongType })
        ) { entry ->
            val gameId = entry.arguments?.getLong("gameId") ?: 0L
            val factory = remember { RetroVaultViewModelFactory.fromApplication(app, gameId) }
            val viewModel: AddGameViewModel = viewModel(factory = factory)
            AddGameScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("gameId") { type = NavType.LongType })
        ) { entry ->
            val gameId = entry.arguments?.getLong("gameId") ?: 0L
            val factory = remember { RetroVaultViewModelFactory.fromApplication(app, gameId) }
            val viewModel: DetailsViewModel = viewModel(factory = factory)
            DetailsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onEdit = { id -> navController.navigate(Screen.EditGame.createRoute(id)) }
            )
        }
    }
}
