package com.example.retrovault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.retrovault.presentation.navigation.RetroVaultNavHost
import com.example.retrovault.presentation.theme.RetroVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetroVaultTheme {
                RetroVaultNavHost()
            }
        }
    }
}
