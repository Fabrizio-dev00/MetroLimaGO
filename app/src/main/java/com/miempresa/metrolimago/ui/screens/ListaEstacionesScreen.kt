package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@Composable
fun ListaEstacionesScreen(
    viewModel: EstacionViewModel,
    navController: NavHostController
) {
    val estaciones by viewModel.estaciones.collectAsState(initial = emptyList())
    val filtro by viewModel.filtro.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { viewModel.cargarDesdeAPI() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5AE0))
        ) {
            Text("Actualizar estaciones desde API", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = filtro,
            onValueChange = { viewModel.setFiltro(it) },
            label = { Text("Buscar estación...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator()
            }
        } else {

            LazyColumn {
                items(estaciones) { estacion ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("detalleEstacion/${estacion.nombre}")
                            },
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F2FF))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = estacion.nombre,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text("Línea ${estacion.linea}")
                            Text(estacion.distrito, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
