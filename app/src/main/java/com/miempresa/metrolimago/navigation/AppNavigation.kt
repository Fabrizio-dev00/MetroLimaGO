package com.miempresa.metrolimago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miempresa.metrolimago.ui.screens.*
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@Composable
fun AppNavigation(viewModel: EstacionViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        // 1. INICIO: La app siempre comienza en la pantalla de Login
        startDestination = "login"
    ) {

        // ==========================================================
        // RUTAS DE ACCESO (PANTALLAS QUE NO NECESITAN EL ViewModel)
        // ==========================================================

        // Pantalla de Bienvenida/Login (basada en tu diseño de Figma)
        composable("login") {
            LoginScreen(navController)
        }

        // ==========================================================
        // RUTAS PRINCIPALES Y DE DETALLE (USAN EL ViewModel)
        // ==========================================================

        // Pantalla Principal (muestra opciones como "Planificar Ruta" y "Ver Estaciones")
        composable("home") {
            HomeScreen(navController)
        }

        // Pantalla del Mapa (si es la que se muestra en tu emulador)
        composable("mapa") {
            MapaScreen(navController)
        }

        // Lista de Estaciones (Muestra todas las estaciones)
        composable("listaEstaciones") {
            ListaEstacionesScreen(viewModel = viewModel, navController = navController)
        }

        // Planificador de Ruta (Donde el usuario selecciona origen y destino)
        composable("planificador") {
            PlanificadorScreen(viewModel = viewModel, navController = navController)
        }

        // Pantalla de Detalle de Estación (Recibe el nombre como argumento)
        composable(
            route = "detalleEstacion/{nombre}",
            arguments = listOf(navArgument("nombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            DetalleEstacionScreen(nombreEstacion = nombre, viewModel = viewModel)
        }

        // ==========================================================
        // RUTAS DE CONFIGURACIÓN
        // ==========================================================

        // Pantalla de Configuración General (Modo oscuro, idioma, etc.)
        composable("configuracion") {
            ConfiguracionScreen(navController)
        }
    }
}