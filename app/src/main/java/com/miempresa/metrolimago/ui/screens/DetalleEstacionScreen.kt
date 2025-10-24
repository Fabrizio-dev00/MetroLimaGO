package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.miempresa.metrolimago.viewmodel.AppViewModel
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@Composable
fun DetalleEstacionScreen(
    nombreEstacion: String,
    viewModel: EstacionViewModel,
    appViewModel: AppViewModel
) {
    val estaciones by viewModel.estaciones.collectAsState(initial = emptyList())
    val estacion = estaciones.find { it.nombre == nombreEstacion }

    if (estacion != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = estacion.nombre,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Línea: ${estacion.linea}")
            Text("Distrito: ${estacion.distrito}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Información adicional sobre la estación...", color = Color.Gray)
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Estación no encontrada", color = Color.Red)
        }
    }
}
