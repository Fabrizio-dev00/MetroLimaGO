package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListaEstacionesScreen(
    estaciones: List<String> = listOf(
        "Villa El Salvador", "María Auxiliadora", "La Cultura", "Gamarra", "Miguel Grau"
    )
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Lista de Estaciones",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(estaciones) { estacion ->
                Card(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(
                        text = estacion,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}