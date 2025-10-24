package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Colores basados en tu diseño (morado/azul para el header)
val PurpleGradient = Brush.verticalGradient(
    listOf(Color(0xFF6C63FF), Color(0xFF3F51B5))
)

@Composable
fun HomeScreen(navController: NavController) {
    // Permite que la pantalla sea desplazable
    Scaffold(
        topBar = { HomeTopBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .background(Color(0xFFF0F0F5)) // Fondo ligeramente gris
        ) {
            // 1. HEADER CON FONDO DEGRADADO (simulando la parte superior curva)
            HomeTopBar(navController)

            // 2. SECCIÓN DE OPCIONES PRINCIPALES (Estaciones y Rutas)
            MainOptionsSection(navController)

            // 3. INFORMACIÓN DEL SERVICIO
            ServiceInfoCard()

            // 4. ALERTAS Y NOTIFICACIONES
            AlertsCard()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


// Componente para la barra superior (simulando el header de Figma)
@Composable
fun HomeTopBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp) // Altura para el header
            .background(PurpleGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "MetroLima GO",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                // Botón de configuración
                IconButton(onClick = { navController.navigate("configuracion") }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configuración",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bienvenido",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Planifica tu viaje de manera rápida y sencilla",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }
}


// Componente para las dos tarjetas principales (Estaciones y Rutas)
@Composable
fun MainOptionsSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            // Mueve la fila hacia arriba para superponerse al header
            .offset(y = (-80).dp)
    ) {
        // Tarjeta de Estaciones
        FeatureCard(
            title = "Estaciones",
            subtitle = "Consulta todas las estaciones",
            icon = Icons.Default.LocationOn,
            modifier = Modifier.weight(1f),
            onClick = { navController.navigate("listaEstaciones") }
        )
        Spacer(modifier = Modifier.width(16.dp))
        // Tarjeta de Rutas
        FeatureCard(
            title = "Rutas",
            subtitle = "Planifica tu recorrido",
            icon = Icons.Default.SwapHoriz,
            modifier = Modifier.weight(1f),
            onClick = { navController.navigate("planificador") }
        )
    }
    // Añade un espacio negativo para compensar el offset y alinear el contenido de abajo
    Spacer(modifier = Modifier.height(16.dp).offset(y = (-80).dp))
}

// Tarjeta reutilizable para Estaciones y Rutas
@Composable
fun FeatureCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .height(150.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6C63FF),
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

// Tarjeta de Información del Servicio (Horario)
@Composable
fun ServiceInfoCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset(y = (-80).dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Información del Servicio",
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            Text(text = "Horario de atención")
            Text(
                text = "Lunes a Domingo: 6:00 AM - 10:00 PM",
                color = Color(0xFF6C63FF),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Tarjeta de Alertas y Notificaciones
@Composable
fun AlertsCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset(y = (-80).dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Alertas y Notificaciones",
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "No hay alertas en este momento",
                color = Color.Gray
            )
        }
    }
}