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

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(navController)
        }

        composable("listaEstaciones") {
            ListaEstacionesScreen(viewModel = viewModel, navController = navController)
        }

        composable(
            route = "detalleEstacion/{nombre}",
            arguments = listOf(navArgument("nombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            DetalleEstacionScreen(nombreEstacion = nombre, viewModel = viewModel)
        }

        composable("planificador") {
            PlanificadorScreen(viewModel = viewModel, navController = navController)
        }
    }
}
