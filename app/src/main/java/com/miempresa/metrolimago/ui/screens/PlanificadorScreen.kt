package com.miempresa.metrolimago.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miempresa.metrolimago.R
import com.miempresa.metrolimago.ui.theme.ButtonColor
import com.miempresa.metrolimago.ui.theme.MetroGradient
import com.miempresa.metrolimago.viewmodel.EstacionViewModel
import com.miempresa.metrolimago.viewmodel.AppViewModel

data class ResultadoRuta(
    val origen: String,
    val destino: String,
    val tiempoEstimadoMin: Int,
    val distanciaKm: Double,
    val numeroParadas: Int,
    val estacionesIntermedias: List<String>,
    val descripcion: String
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorScreen(viewModel: EstacionViewModel, navController: NavHostController, appViewModel: AppViewModel) {
    val estaciones by viewModel.estaciones.collectAsState()
    val nombresEstaciones = remember(estaciones) { estaciones.map { it.nombre } }

    var origen by remember { mutableStateOf<String?>(null) }
    var destino by remember { mutableStateOf<String?>(null) }
    var rutaCalculada by remember { mutableStateOf<ResultadoRuta?>(null) }

    LaunchedEffect(nombresEstaciones) {
        if (origen == null && nombresEstaciones.isNotEmpty()) {
            origen = nombresEstaciones.first()
        }
        if (destino == null && nombresEstaciones.size > 1) {
            destino = nombresEstaciones.last()
        }
    }

    Scaffold(
        topBar = { PlanificadorTopBar(navController) },
        containerColor = Color.Transparent,
        modifier = Modifier.background(MetroGradient)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            StationDropdown(
                label = stringResource(R.string.origin_station_label),
                currentSelection = origen,
                options = nombresEstaciones,
                onSelect = { origen = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(
                onClick = {
                    val temp = origen
                    origen = destino
                    destino = temp
                    rutaCalculada = null
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SwapVert,
                    contentDescription = stringResource(R.string.swap_stations_description),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            StationDropdown(
                label = stringResource(R.string.destination_station_label),
                currentSelection = destino,
                options = nombresEstaciones,
                onSelect = { destino = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            val isButtonEnabled = origen != null && destino != null && origen != destino
            Button(
                onClick = {
                    if (isButtonEnabled) {
                        rutaCalculada = viewModel.calcularRuta(origen!!, destino!!, estaciones)
                    }
                },
                enabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    stringResource(R.string.calculate_route_button),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = rutaCalculada != null) {
                rutaCalculada?.let { resultado ->
                    ResultadoRutaCard(resultado)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorTopBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(stringResource(R.string.plan_route_title), color = Color.White, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.content_description_back), tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDropdown(
    label: String,
    currentSelection: String?,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label, color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(4.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !it }
        ) {
            OutlinedTextField(
                value = currentSelection ?: stringResource(R.string.select_station_placeholder),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                // --- ¡LA CORRECCIÓN ESTÁ AQUÍ! ---
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White.copy(0.9f),
                    unfocusedContainerColor = Color.White.copy(0.9f)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            ExposedDropdownMenu(expanded, { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = { onSelect(it); expanded = false }
                    )
                }
            }
        }
    }
}

@Composable
fun ResultadoRutaCard(resultado: ResultadoRuta) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(resultado.origen, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Icon(Icons.Default.ArrowForward, contentDescription = stringResource(R.string.arrow_to_description), tint = Color.White)
                Text(resultado.destino, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                InfoItem(icon = Icons.Default.Timer, label = stringResource(R.string.duration_label), value = "~${resultado.tiempoEstimadoMin} min")
                InfoItem(icon = Icons.Default.Route, label = stringResource(R.string.distance_label), value = "%.1f km".format(resultado.distanciaKm))
                InfoItem(icon = Icons.Default.Subway, label = stringResource(R.string.stops_label), value = resultado.numeroParadas.toString())
            }
            Divider(Modifier.padding(vertical = 16.dp), color = Color.White.copy(alpha = 0.3f))
            Text(stringResource(R.string.stations_on_route_label), color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            resultado.estacionesIntermedias.forEach {
                Text("• $it", color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(28.dp))
        Spacer(Modifier.height(4.dp))
        Text(value, color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(label, color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.bodySmall)
    }
}
