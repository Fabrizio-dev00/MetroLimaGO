package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miempresa.metrolimago.ui.components.*
import com.miempresa.metrolimago.ui.theme.MetroLimaGOTheme
import com.miempresa.metrolimago.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    appViewModel: AppViewModel // 👈 Recibe el ViewModel global
) {
    // 🟢 Escuchamos el idioma actual
    val isSpanish by appViewModel.isSpanish.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = appViewModel.getString("MetroLima GO", "MetroLima GO"),
                onSettingsClick = { navController.navigate("configuracion") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // --- Encabezado principal ---
            HeaderSection(
                title = appViewModel.getString("Bienvenido", "Welcome"),
                subtitle = appViewModel.getString(
                    "Planifica tu viaje de manera rápida y sencilla",
                    "Plan your trip quickly and easily"
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Sección principal de opciones ---
            MainOptionsSection(
                navController = navController,
                appViewModel = appViewModel
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Nueva sección de información de servicio ---
            ServiceInfoCard(
                title = appViewModel.getString("Información del servicio", "Service Information"),
                subtitle = appViewModel.getString(
                    "Conoce el estado de las líneas del Metro",
                    "Check the current metro line status"
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Nueva sección de alertas ---
            AlertsCard(
                title = appViewModel.getString("Alertas", "Alerts"),
                subtitle = appViewModel.getString(
                    "Revisa si hay interrupciones o mantenimiento",
                    "Check for interruptions or maintenance"
                )
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


