package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miempresa.metrolimago.viewmodel.EstacionViewModel
import com.miempresa.metrolimago.ui.theme.MetroGradient // Usamos el Gradiente de tu Theme.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorScreen(viewModel: EstacionViewModel, navController: NavHostController) {
    val estaciones by viewModel.estaciones.collectAsState()

    // Convertir la lista de estaciones a nombres de estaciones para los Dropdowns
    val nombresEstaciones = remember(estaciones) { estaciones.map { it.nombre } }

    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }

    // Estado del resultado de la ruta (Ejemplo: Simular un resultado)
    var rutaCalculada by remember { mutableStateOf<RutaResultado?>(null) }

    // Cargar las estaciones remotas si no están cargadas
    LaunchedEffect(Unit) {
        if (estaciones.isEmpty()) {
            viewModel.cargarDesdeAPI()
        }
    }

    Scaffold(
        topBar = { PlanificadorTopBar(navController) },
        modifier = Modifier.background(Color(0xFFF0F0F5))
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. SELECTOR DE ORIGEN
            StationDropdown(
                label = "Estación de origen",
                currentSelection = origen,
                options = nombresEstaciones,
                onSelect = { origen = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. INTERCAMBIADOR DE RUTA (Ícono de doble flecha)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-8).dp), // Subir un poco para acercarlo a los campos
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = {
                        // Intercambiar valores
                        val temp = origen
                        origen = destino
                        destino = temp
                    },
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFD3D3D3), RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapVert,
                        contentDescription = "Intercambiar",
                        tint = Color.Black.copy(alpha = 0.7f)
                    )
                }
            }

            // 3. SELECTOR DE DESTINO
            StationDropdown(
                label = "Estación de destino",
                currentSelection = destino,
                options = nombresEstaciones,
                onSelect = { destino = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. BOTÓN CALCULAR RUTA
            val isButtonEnabled = origen.isNotBlank() && destino.isNotBlank() && origen != destino
            Button(
                onClick = {
                    // 🚨 Aquí debes llamar a tu RouteCalculator:
                    // val resultado = RouteCalculator.calcularRuta(origen, destino, estaciones)
                    // rutaCalculada = ResultadoRuta(tiempo = resultado.tiempo, pasos = resultado.pasos)

                    // Simulación de Cálculo (reemplaza esto con la llamada real)
                    if(isButtonEnabled) {
                        rutaCalculada = RutaResultado(
                            tiempoEstimado = "18 min",
                            distancia = "9.4 km",
                            estacionesIntermedias = listOf("Cabitos", "Ayacucho", "Atocongo")
                        )
                    }
                },
                enabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
            ) {
                Text("Calcular Ruta", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. TARJETA DE RESULTADO (Se muestra solo si rutaCalculada no es null)
            rutaCalculada?.let { resultado ->
                ResultadoRutaCard(resultado)
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// COMPONENTES AUXILIARES
// -------------------------------------------------------------------------------------------------

@Composable
fun PlanificadorTopBar(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MetroGradient)
            .padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.White
                )
            }
            Text(
                text = "Planificar Ruta",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDropdown(
    label: String,
    currentSelection: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Usamos ExposedDropdownMenuBox para el diseño limpio del Figma
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            // El campo de texto que siempre se muestra
            value = currentSelection.ifEmpty { "" }, // Muestra el texto seleccionado
            onValueChange = {}, // No se edita directamente
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        // El menú desplegable que aparece
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onSelect(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun ResultadoRutaCard(resultado: RutaResultado) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Resultado de la Ruta",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF3F51B5)
            )
            Spacer(Modifier.height(16.dp))

            // Tiempo y Distancia
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    label = "Tiempo estimado",
                    value = resultado.tiempoEstimado,
                    modifier = Modifier.weight(1f)
                )
                InfoItem(
                    label = "Distancia aproximada",
                    value = resultado.distancia,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Estaciones Intermedias
            Text(
                text = "Estaciones intermedias:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = resultado.estacionesIntermedias.joinToString(", "),
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun InfoItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        Text(value, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

// Clase de datos para simular el resultado de tu RouteCalculator
data class RutaResultado(
    val tiempoEstimado: String,
    val distancia: String,
    val estacionesIntermedias: List<String>
)