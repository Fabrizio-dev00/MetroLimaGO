package com.miempresa.metrolimago.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNav(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Rutas") },
            label = { Text("Rutas") },
            selected = false,
            onClick = { navController.navigate("planificador") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text("Config") },
            selected = false,
            onClick = { navController.navigate("configuracion") }
        )
    }
}
