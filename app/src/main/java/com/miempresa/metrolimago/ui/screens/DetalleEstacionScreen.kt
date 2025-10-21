package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetalleEstacionScreen(
    nombreEstacion: String = "María Auxiliadora",
    descripcion: String = "Estación moderna con conexión a líneas de bus y servicios cercanos.",
    id: Int?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = nombreEstacion,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}