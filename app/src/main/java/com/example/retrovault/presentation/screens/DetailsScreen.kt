package com.example.retrovault.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.retrovault.domain.model.Game
import com.example.retrovault.presentation.components.EmptyStateComponent
import com.example.retrovault.presentation.components.LoadingComponent
import com.example.retrovault.presentation.components.RetroTopBar
import com.example.retrovault.presentation.viewmodel.DetailsViewModel
import com.example.retrovault.presentation.viewmodel.UiState

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { RetroTopBar(title = "Detalhes", showBack = true, onBackClick = onBack) }
    ) { padding ->
        when (state) {
            UiState.Loading -> LoadingComponent()
            is UiState.Error -> EmptyStateComponent(title = "Erro", subtitle = "Detalhes indisponiveis")
            UiState.Empty -> EmptyStateComponent()
            is UiState.Success -> {
                val game = (state as UiState.Success<Game>).data
                DetailsContent(game = game, modifier = Modifier.padding(padding))
            }
        }
    }
}

@Composable
private fun DetailsContent(game: Game, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            tonalElevation = 2.dp
        ) {}

        Text(text = game.name, style = MaterialTheme.typography.titleLarge)
        Text(text = "Plataforma: ${game.platform}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Ano: ${game.year}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Nota: ${game.rating}", style = MaterialTheme.typography.bodyMedium)
    }
}


