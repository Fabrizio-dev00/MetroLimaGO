package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ConfiguracionScreen(navController: NavHostController) {
    var darkMode by remember { mutableStateOf(false) }
    var idioma by remember { mutableStateOf("Español") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Configuración", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Modo oscuro")
            Switch(checked = darkMode, onCheckedChange = { darkMode = it })
        }

        Spacer(Modifier.height(24.dp))
        Text("Idioma")
        DropdownMenu(
            expanded = false,
            onDismissRequest = { },
        ) { }

        Spacer(Modifier.height(24.dp))
        Text("MetroLima GO v1.0.0", fontWeight = FontWeight.Medium)
        Text("Desarrollado por el equipo de Desarrollo UTP", fontSize = 12.sp)
    }
}
