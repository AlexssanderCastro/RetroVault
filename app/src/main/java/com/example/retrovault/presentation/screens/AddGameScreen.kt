package com.example.retrovault.presentation.screens

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import com.example.retrovault.presentation.components.RetroTopBar
import com.example.retrovault.presentation.viewmodel.AddGameViewModel
import com.example.retrovault.presentation.viewmodel.FormEvent

@Composable
fun AddGameScreen(
    viewModel: AddGameViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val form = state.form
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.updateImageUri(uri?.toString())
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is FormEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
                FormEvent.NavigateBack -> onBack()
            }
        }
    }

    Scaffold(
        topBar = {
            RetroTopBar(
                title = if (state.isEditing) "Editar jogo" else "Cadastrar jogo",
                showBack = true,
                onBackClick = onBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = form.name,
                onValueChange = viewModel::updateName,
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.platform,
                onValueChange = viewModel::updatePlatform,
                label = { Text("Plataforma") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.year,
                onValueChange = viewModel::updateYear,
                label = { Text("Ano") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.genre,
                onValueChange = viewModel::updateGenre,
                label = { Text("Genero") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.developer,
                onValueChange = viewModel::updateDeveloper,
                label = { Text("Desenvolvedora") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.rating,
                onValueChange = viewModel::updateRating,
                label = { Text("Nota (0-10)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.notes,
                onValueChange = viewModel::updateNotes,
                label = { Text("Observacoes") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            if (form.imageUri.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(form.imageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto do jogo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        photoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (form.imageUri.isNullOrBlank()) "Selecionar foto" else "Trocar foto")
                }
                if (!form.imageUri.isNullOrBlank()) {
                    OutlinedButton(
                        onClick = { viewModel.updateImageUri(null) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Remover foto")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = viewModel::save,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSaving
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Salvar")
                }
            }
            Text(
                text = "Campos obrigatorios: Nome e Plataforma",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
