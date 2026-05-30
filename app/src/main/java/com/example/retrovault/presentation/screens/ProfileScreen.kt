package com.example.retrovault.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(onBack: () -> Unit = {}) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Perfil", style = MaterialTheme.typography.titleLarge)
            Text(text = "Nome do usuário local: Usuário (local)")
            Text(text = "Quantidade de jogos: --")
            Text(text = "Quantidade zerados: --")
            Text(text = "Quantidade favoritos: --")
        }
    }
}
