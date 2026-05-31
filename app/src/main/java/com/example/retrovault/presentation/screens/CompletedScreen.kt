package com.example.retrovault.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrovault.presentation.components.EmptyStateComponent
import com.example.retrovault.presentation.components.GameCard
import com.example.retrovault.presentation.components.LoadingComponent
import com.example.retrovault.presentation.components.RetroBottomNavigation
import com.example.retrovault.presentation.components.RetroTopBar
import com.example.retrovault.presentation.viewmodel.CompletedViewModel

@Composable
fun CompletedScreen(
    viewModel: CompletedViewModel,
    onHome: () -> Unit,
    onGameSelected: (Long) -> Unit,
    onFavorites: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        topBar = { RetroTopBar(title = "Zerados") },
        bottomBar = { RetroBottomNavigation(selectedIndex = 2, onItemSelected = { if (it == 0) onHome() else if (it == 1) onFavorites() }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        when {
            state.isLoading -> LoadingComponent()
            state.games.isEmpty() -> EmptyStateComponent(
                title = "Nenhum jogo zerado",
                subtitle = "Marque um jogo como zerado na tela de detalhes"
            )
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 24.dp)) {
                        items(state.games) { game ->
                            GameCard(
                                game = game,
                                onClick = { onGameSelected(game.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
