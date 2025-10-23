package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miempresa.metrolimago.R

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.metro_lima_mapa),
            contentDescription = "Mapa del Metro de Lima",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "MetroLima GO",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Planifica tus viajes en el Metro de Lima",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { navController.navigate("listaEstaciones") }) {
                Text(text = "Ver estaciones")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("planificador") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5AE0))
            ) {
                Text("Planificar ruta", color = Color.White)
            }
        }
    }
}
