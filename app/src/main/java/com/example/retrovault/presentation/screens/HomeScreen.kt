package com.example.retrovault.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.example.retrovault.presentation.components.RetroSearchBar
import com.example.retrovault.presentation.components.RetroTopBar
import com.example.retrovault.presentation.viewmodel.HomeUiState
import com.example.retrovault.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddGame: () -> Unit,
    onGameSelected: (Long) -> Unit,
    onFavorites: () -> Unit,
    onCompleted: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        topBar = { RetroTopBar(title = "RetroVault") },
        bottomBar = { RetroBottomNavigation(selectedIndex = 0, onItemSelected = { if (it == 1) onFavorites() else if (it == 2) onCompleted() }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddGame) {
                androidx.compose.material3.Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        HomeContent(
            state = state,
            onQueryChange = viewModel::updateQuery,
            onGameSelected = onGameSelected,
            onToggleFavorite = viewModel::toggleFavorite,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onQueryChange: (String) -> Unit,
    onGameSelected: (Long) -> Unit,
    onToggleFavorite: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val games = state.games
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWide = maxWidth > 600.dp
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            RetroSearchBar(value = state.query, onValueChange = onQueryChange)
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                LoadingComponent()
                return@Column
            }

            if (games.isEmpty()) {
                EmptyStateComponent(
                    title = if (state.query.isBlank()) "Nenhum jogo encontrado" else "Nenhum resultado",
                    subtitle = if (state.query.isBlank()) {
                        "Adicione seu primeiro jogo para começar"
                    } else {
                        "Tente outra pesquisa"
                    }
                )
                return@Column
            }

            if (isWide) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 280.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(games.size) { index ->
                        val game = games[index]
                        GameCard(
                            game = game,
                            onClick = { onGameSelected(game.id) },
                            onToggleFavorite = { onToggleFavorite(game.id) }
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(games) { game ->
                        GameCard(
                            game = game,
                            onClick = { onGameSelected(game.id) },
                            onToggleFavorite = { onToggleFavorite(game.id) }
                        )
                    }
                }
            }
        }
    }
}
