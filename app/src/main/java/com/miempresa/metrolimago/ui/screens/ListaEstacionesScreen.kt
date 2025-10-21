package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.miempresa.metrolimago.viewmodel.EstacionViewModel



@Composable
fun ListaEstacionesScreen(viewModel: EstacionViewModel) {
    val estaciones by viewModel.estaciones.collectAsState(initial = emptyList())
    val contexto = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = viewModel.filtro.collectAsState().value,
            onValueChange = { viewModel.setFiltro(it) },
            label = { Text("Buscar estación...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(estaciones) { estacion ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(estacion.nombre, fontWeight = FontWeight.Bold)
                        Text("Línea ${estacion.linea}")
                        Text(estacion.distrito, color = Color.Gray)
                    }
                }
            }
        }
    }
}