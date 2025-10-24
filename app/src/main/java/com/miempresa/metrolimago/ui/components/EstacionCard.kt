package com.miempresa.metrolimago.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EstacionCard(nombre: String, linea: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Línea: $linea", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}