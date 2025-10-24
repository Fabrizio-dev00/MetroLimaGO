package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.ui.theme.BackgroundLight
import com.miempresa.metrolimago.ui.theme.ButtonColor
import com.miempresa.metrolimago.ui.theme.MetroGradient
import com.miempresa.metrolimago.viewmodel.AppViewModel
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEstacionesScreen(viewModel: EstacionViewModel, navController: NavHostController, appViewModel: AppViewModel) {

    // 1. Recolectamos los estados del ViewModel. ¡Eso es todo lo que necesitamos!
    val estaciones by viewModel.estaciones.collectAsState() // <-- Esta lista ya viene filtrada por el ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val filtro by viewModel.filtro.collectAsState() // <-- Usamos el filtro del ViewModel

    // Este LaunchedEffect es correcto, se asegura de que los datos se pidan una vez.
    LaunchedEffect(Unit) {
        viewModel.cargarDesdeAPI()
    }

    Scaffold(
        topBar = {
            EstacionesTopBar(
                navController = navController,
                filtro = filtro, // <-- Pasamos el filtro del ViewModel
                onFiltroChange = { nuevoFiltro ->
                    viewModel.setFiltro(nuevoFiltro) // <-- Actualizamos el filtro en el ViewModel
                }
            )
        },
        modifier = Modifier.background(BackgroundLight)
    ) { paddingValues ->

        // 2. Ya no necesitamos 'listaFiltrada'. La lista 'estaciones' ya está filtrada.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        color = ButtonColor,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // 3. La lógica ahora es más simple.
                estaciones.isEmpty() && filtro.isEmpty() -> {
                    Text("Cargando estaciones o no hay datos.", Modifier.align(Alignment.Center))
                }
                estaciones.isEmpty() && filtro.isNotEmpty() -> {
                    Text("No se encontraron estaciones con ese filtro 😢", Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // 4. Usamos directamente la lista 'estaciones'.
                        items(estaciones) { estacion ->
                            EstacionCardRediseñada(estacion, navController)
                        }
                    }
                }
            }
        }
    }
}

// ... (El resto de tu archivo, EstacionesTopBar y EstacionCardRediseñada, está perfecto y no necesita cambios)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstacionesTopBar(
    navController: NavHostController,
    filtro: String,
    onFiltroChange: (String) -> Unit
) {
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
                text = "Estaciones",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        OutlinedTextField(
            value = filtro,
            onValueChange = onFiltroChange,
            placeholder = { Text("Buscar estación...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = ButtonColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp)
        )
    }
}



@Composable
fun EstacionCardRediseñada(estacion: Estacion, navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("detalleEstacion/${estacion.nombre}")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = estacion.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Contenedor de la Línea (Simula la etiqueta azul)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(containerColor = ButtonColor) // 🟢 Usando el color del tema
                    ) {
                        Text(
                            text = "Línea 1",
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Distrito
                    Text(
                        text = estacion.distrito,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // Icono de Ubicación a la derecha
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Ubicación",
                tint = Color.LightGray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
