package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEstacionesScreen(viewModel: EstacionViewModel) {

    val estaciones by viewModel.estacionesRemoto.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var filtro by remember { mutableStateOf("") }

    // Cargar las estaciones remotas solo una vez
    LaunchedEffect(Unit) {
        viewModel.cargarDesdeAPI()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Estaciones del Metro de Lima 🚇",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo de búsqueda
        OutlinedTextField(
            value = filtro,
            onValueChange = { filtro = it },
            label = { Text("Buscar estación...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            // Mientras carga la API
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF6A5AE0))
            }
        } else {
            // Lista filtrada
            val listaFiltrada = estaciones.filter {
                it.nombre.contains(filtro, ignoreCase = true) ||
                        it.distrito.contains(filtro, ignoreCase = true)
            }

            if (listaFiltrada.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron estaciones 😢")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listaFiltrada) { estacion ->
                        EstacionCard(estacion)
                    }
                }
            }
        }
    }
}

@Composable
fun EstacionCard(estacion: Estacion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = estacion.nombre,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text("Línea: ${estacion.linea}", color = Color.Gray)
            Text("Distrito: ${estacion.distrito}", color = Color.Gray)
        }
    }
}
