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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
    onGameSelected: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        topBar = { RetroTopBar(title = "RetroVault") },
        bottomBar = { RetroBottomNavigation() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddGame) {
                androidx.compose.material3.Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingComponent()
            state.games.isEmpty() -> EmptyStateComponent()
            else -> {
                HomeContent(
                    state = state,
                    onQueryChange = viewModel::updateQuery,
                    onGameSelected = onGameSelected,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onQueryChange: (String) -> Unit,
    onGameSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val games = state.games
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWide = maxWidth > 600.dp
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            RetroSearchBar(value = state.query, onValueChange = onQueryChange)
            Spacer(modifier = Modifier.height(16.dp))
            StatsHighlight(state = state)
            Spacer(modifier = Modifier.height(16.dp))

            if (isWide) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 280.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(games.size) { index ->
                        val game = games[index]
                        GameCard(game = game, onClick = { onGameSelected(game.id) })
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(games) { game ->
                        GameCard(game = game, onClick = { onGameSelected(game.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsHighlight(state: HomeUiState) {
    val platformSummary = if (state.stats.platformCounts.isEmpty()) {
        "Nenhuma plataforma cadastrada"
    } else {
        state.stats.platformCounts.take(3).joinToString(" | ") { "${it.first}: ${it.second}" }
    }
    val latestSummary = if (state.stats.latestGames.isEmpty()) {
        "Nenhuma adicao recente"
    } else {
        state.stats.latestGames.joinToString(", ") { it.name }
    }

    Surface(
        shape = MaterialTheme.shapes.large,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Resumo da colecao", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Jogos cadastrados: ${state.stats.totalGames}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Plataformas: $platformSummary", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Ultimas adicoes: $latestSummary", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
