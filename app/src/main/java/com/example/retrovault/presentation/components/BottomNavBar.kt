package com.example.retrovault.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RetroBottomNavigation(selectedIndex: Int = 0, onItemSelected: (Int) -> Unit = {}) {
    val items = listOf("Home", "Favoritos")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Star)

    NavigationBar {
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = { androidx.compose.material3.Icon(icons[index], contentDescription = label) },
                label = { Text(label) }
            )
        }
    }
}

