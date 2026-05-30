package com.example.retrovault.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.retrovault.domain.model.Game
import com.example.retrovault.presentation.theme.RetroNeonBlue
import com.example.retrovault.presentation.theme.RetroNeonPurple

@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(listOf(RetroNeonPurple, RetroNeonBlue))
                )
                .padding(1.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {}
                Spacer(modifier = Modifier.size(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = game.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "${game.platform} - ${game.year}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Nota: ${game.rating}", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

