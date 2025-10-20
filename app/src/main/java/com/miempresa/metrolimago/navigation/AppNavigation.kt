package com.miempresa.metrolimago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miempresa.metrolimago.ui.screens.*

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("lista") { ListaEstacionesScreen() }
        composable("detalle") { DetalleEstacionScreen() }
    }
}