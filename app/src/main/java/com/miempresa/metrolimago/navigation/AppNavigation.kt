package com.miempresa.metrolimago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            ListaEstacionesScreen(viewModel = viewModel)
        }
        composable("detalleEstacion/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt()
            DetalleEstacionScreen(id = id)
        }
    }
}