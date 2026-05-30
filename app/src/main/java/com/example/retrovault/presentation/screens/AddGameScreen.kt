package com.example.retrovault.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrovault.presentation.components.RetroTopBar
import com.example.retrovault.presentation.viewmodel.AddGameViewModel

@Composable
fun AddGameScreen(
    viewModel: AddGameViewModel,
    onBack: () -> Unit
) {
    val form by viewModel.form.collectAsState()

    Scaffold(
        topBar = { RetroTopBar(title = "Cadastrar jogo", showBack = true, onBackClick = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
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
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = form.rating,
                onValueChange = viewModel::updateRating,
                label = { Text("Nota") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Salvar")
            }
            Text(
                text = "Funcionalidade em construcao",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

