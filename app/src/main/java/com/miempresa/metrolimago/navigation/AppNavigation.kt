package com.miempresa.metrolimago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miempresa.metrolimago.ui.screens.*
import com.miempresa.metrolimago.viewmodel.AppViewModel
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

@Composable
fun AppNavigation(
    viewModel: EstacionViewModel,
    appViewModel: AppViewModel // ✅ Recibimos el ViewModel global
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // 🔹 Pantalla de login
        composable("login") {
            LoginScreen(navController = navController)
        }

        // 🔹 Pantalla principal
        composable("home") {
            HomeScreen(
                navController = navController,
                appViewModel = appViewModel
            )
        }

        // 🔹 Pantalla de mapa
        composable("mapa") {
            MapaScreen(navController = navController)
        }

        // 🔹 Lista de estaciones
        composable("listaEstaciones") {
            ListaEstacionesScreen(
                viewModel = viewModel,
                navController = navController,
                appViewModel = appViewModel // ✅ Se pasa también aquí
            )
        }

        // 🔹 Planificador
        composable("planificador") {
            PlanificadorScreen(
                viewModel = viewModel,
                navController = navController,
                appViewModel = appViewModel // ✅ También aquí
            )
        }

        // 🔹 Detalle de estación
        composable(
            route = "detalleEstacion/{nombre}",
            arguments = listOf(navArgument("nombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            DetalleEstacionScreen(
                nombreEstacion = nombre,
                viewModel = viewModel,
                appViewModel = appViewModel // ✅ Se agrega aquí también
            )
        }

        // 🔹 Configuración (idioma y tema)
        composable("configuracion") {
            ConfiguracionScreen(
                navController = navController,
                appViewModel = appViewModel
            )
        }
    }
}
