package com.miempresa.metrolimago.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miempresa.metrolimago.ui.theme.BackgroundLight
import com.miempresa.metrolimago.ui.theme.ButtonColor // Asegúrate de que este color esté definido

@Composable
fun MainOptionsSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Tarjeta de Estaciones
        OptionCard(
            title = "Estaciones",
            subtitle = "Consulta todas las estaciones",
            icon = Icons.Default.LocationOn,
            onClick = { navController.navigate("listaEstaciones") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Tarjeta de Rutas
        OptionCard(
            title = "Rutas",
            subtitle = "Planifica tu recorrido",
            icon = Icons.Default.SwapHoriz,
            onClick = { navController.navigate("planificador") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun OptionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = BackgroundLight), // Usa un color de fondo claro
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ButtonColor, // Usa tu color principal de botón/acento
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, maxLines = 1)
        }
    }
}