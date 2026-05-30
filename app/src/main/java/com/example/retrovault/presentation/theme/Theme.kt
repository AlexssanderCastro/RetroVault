package com.example.retrovault.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = RetroNeonPurple,
    secondary = RetroNeonBlue,
    tertiary = RetroNeonPink,
    background = RetroBlack,
    surface = RetroSurface,
    surfaceVariant = RetroDarkGray,
    outline = RetroOutline,
    onPrimary = RetroOnDark,
    onSecondary = RetroOnDark,
    onTertiary = RetroOnDark,
    onBackground = RetroOnDark,
    onSurface = RetroOnDark,
    onSurfaceVariant = RetroOnDarkMuted
)

private val LightColorScheme = lightColorScheme(
    primary = RetroNeonPurple,
    secondary = RetroNeonBlue,
    tertiary = RetroNeonPink
)

@Composable
fun RetroVaultTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme || !dynamicColor) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
