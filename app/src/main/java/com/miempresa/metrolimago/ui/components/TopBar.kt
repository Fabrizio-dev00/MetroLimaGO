package com.miempresa.metrolimago.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Añadimos un parámetro 'onSettingsClick' para manejar la navegación desde la pantalla que usa el TopBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    // La función que se ejecutará cuando se haga clic en el ícono de configuración
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title, color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF6A5AE0)
        ),
        actions = {
            // 🟢 Botón de Configuración
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = Color.White
                )
            }
        }
    )
}
