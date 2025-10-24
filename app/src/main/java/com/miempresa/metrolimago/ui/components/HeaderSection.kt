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
fun HeaderSection(
    title: String,      // 👈 Se agregan estos dos parámetros
    subtitle: String
) {
    // Degradado de colores morado y azul
    val purpleGradient = Brush.verticalGradient(
        listOf(Color(0xFF673AB7), Color(0xFF42A5F5))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(purpleGradient)
            .padding(top = 16.dp, start = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title, // 👈 Texto dinámico
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle, // 👈 Texto dinámico
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
