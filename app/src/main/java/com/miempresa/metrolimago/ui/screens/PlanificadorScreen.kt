package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.model.Ruta
import com.miempresa.metrolimago.ui.components.DropdownSelector
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorScreen(
    viewModel: EstacionViewModel,
    navController: NavController
) {
    val estaciones by viewModel.estaciones.collectAsState()
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var ruta by remember { mutableStateOf<Ruta?>(null) }

    val gradiente = Brush.verticalGradient(
        listOf(Color(0xFF6A5AE0), Color(0xFF4FC3F7))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificador de Ruta 🚇", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF6A5AE0)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradiente)
                .padding(padding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecciona tu origen y destino",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    DropdownSelector(
                        label = "Origen",
                        opciones = estaciones.map { it.nombre },
                        seleccionActual = origen,
                        onSelect = { origen = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DropdownSelector(
                        label = "Destino",
                        opciones = estaciones.map { it.nombre },
                        seleccionActual = destino,
                        onSelect = { destino = it }
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            if (origen.isNotEmpty() && destino.isNotEmpty()) {
                                ruta = viewModel.calcularRuta(origen, destino, estaciones)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5AE0))
                    ) {
                        Text("Calcular Ruta", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    ruta?.let {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "🕒 Tiempo estimado: ${it.tiempo} min",
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("Recorrido:")
                                Spacer(Modifier.height(8.dp))
                                it.pasos.forEachIndexed { index, paso ->
                                    Text("${index + 1}. $paso")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}