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
        startDestination = "login"
    ) {
        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(navController)
        }

        // Pantalla principal después del login
        composable("home") {
            HomeScreen(navController)
        }

        // Mapa con estaciones
        composable("mapa") {
            MapaScreen(navController)
        }

        // Lista de estaciones desde la base de datos
        composable("listaEstaciones") {
            ListaEstacionesScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        // Detalle de una estación seleccionada
        composable(
            route = "detalleEstacion/{nombre}",
            arguments = listOf(navArgument("nombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            DetalleEstacionScreen(
                nombreEstacion = nombre,
                viewModel = viewModel
            )
        }

        // Pantalla de configuración
        composable("configuracion") {
            ConfiguracionScreen(navController)
        }

        // Pantalla principal con navegación general
        composable("principal") {
            PantallaPrincipalScreen(navController)
        }
    }
}
