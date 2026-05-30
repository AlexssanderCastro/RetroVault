package com.example.retrovault.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.retrovault.domain.model.Game
import com.example.retrovault.presentation.components.EmptyStateComponent
import com.example.retrovault.presentation.components.LoadingComponent
import com.example.retrovault.presentation.components.RetroTopBar
import com.example.retrovault.presentation.viewmodel.DetailsViewModel
import com.example.retrovault.presentation.viewmodel.FormEvent
import com.example.retrovault.presentation.viewmodel.UiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is FormEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
                FormEvent.NavigateBack -> onBack()
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Excluir jogo") },
            text = { Text("Tem certeza que deseja excluir este jogo?") },
            confirmButton = {
                Button(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteGame()
                }) { Text("Excluir") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = { RetroTopBar(title = "Detalhes", showBack = true, onBackClick = onBack) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        when (state) {
            UiState.Loading -> LoadingComponent()
            is UiState.Error -> EmptyStateComponent(title = "Erro", subtitle = "Detalhes indisponiveis")
            UiState.Empty -> EmptyStateComponent()
            is UiState.Success -> {
                val game = (state as UiState.Success<Game>).data
                DetailsContent(
                    game = game,
                    onEdit = { onEdit(game.id) },
                    onDelete = { showDeleteDialog = true },
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
private fun DetailsContent(
    game: Game,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateLabel = remember(game.createdAt) {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.format(Date(game.createdAt))
    }

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
        Text(text = "Genero: ${game.genre}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Desenvolvedora: ${game.developer}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Nota: ${game.rating}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Observacoes: ${game.notes}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Data de cadastro: $dateLabel", style = MaterialTheme.typography.bodyMedium)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(onClick = onEdit, modifier = Modifier.weight(1f)) {
                Text("Editar")
            }
            Button(onClick = onDelete, modifier = Modifier.weight(1f)) {
                Text("Excluir")
            }
        }
    }
}
