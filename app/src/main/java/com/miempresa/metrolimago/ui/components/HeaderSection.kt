package com.miempresa.metrolimago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeaderSection() {
    // Colores basados en tu diseño degradado (morado/azul)
    val purpleGradient = Brush.verticalGradient(
        listOf(Color(0xFF673AB7), Color(0xFF42A5F5))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Altura para cubrir la curva simulada
            .background(purpleGradient)
            .padding(top = 16.dp, start = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Bienvenido",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Planifica tu viaje de manera rápida y sencilla",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}