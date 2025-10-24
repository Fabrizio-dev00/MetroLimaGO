package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miempresa.metrolimago.R
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasScreen(
    viewModel: EstacionViewModel,
    navController: NavController
) {
    // --- Y AQUÍ TAMBIÉN, LA MISMA LÍNEA MÁGICA ---
    val estaciones by viewModel.estaciones.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.options_routes_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        // Si la lista de estaciones está vacía, mostramos el indicador de carga.
        if (estaciones.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Cuando hay datos, mostramos el contenido del planificador de rutas.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Planificador de Rutas",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Ejemplo de cómo usarías la lista:
                Text("Estación de Origen:")
                // DropdownMenu(... con 'estaciones.map { it.nombre }' ...)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Estación de Destino:")
                // DropdownMenu(... con 'estaciones.map { it.nombre }' ...)
            }
        }
    }
}
