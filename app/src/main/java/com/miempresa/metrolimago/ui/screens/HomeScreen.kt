package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miempresa.metrolimago.ui.components.AlertsCard
import com.miempresa.metrolimago.ui.components.HeaderSection
import com.miempresa.metrolimago.ui.components.MainOptionsSection
import com.miempresa.metrolimago.ui.components.ServiceInfoCard
import com.miempresa.metrolimago.ui.theme.MetroLimaGOTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection()

            Spacer(modifier = Modifier.height(24.dp))

            MainOptionsSection(navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            ServiceInfoCard()

            Spacer(modifier = Modifier.height(16.dp))

            AlertsCard()

            Spacer(modifier = Modifier.height(24.dp))

            // 👇 NUEVO BOTÓN PARA IR AL MAPA
            Button(
                onClick = { navController.navigate("mapa") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Ver mapa del Metro de Lima")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MetroLimaGOTheme {
        HomeScreen(navController = rememberNavController())
    }
}
