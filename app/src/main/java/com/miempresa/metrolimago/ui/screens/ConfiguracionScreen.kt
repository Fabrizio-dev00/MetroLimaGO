package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.ExitToApp // Necesario para el ícono del botón
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miempresa.metrolimago.viewmodel.AppViewModel
import android.util.Log // Import necesario para el Log.d

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionScreen(
    navController: NavHostController,
    appViewModel: AppViewModel
) {
    // 🟢 Observar estados globales
    val isSpanish by appViewModel.isSpanish.collectAsState()
    val isDarkTheme by appViewModel.isDarkTheme.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appViewModel.getString("Configuración", "Settings"), color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A5AE0)
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        // 1. Usamos un Box principal para poder anclar el botón al fondo.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // 2. El contenido de la configuración va en un Column con scroll.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(24.dp))

                // --- 1️⃣ APARIENCIA (Modo oscuro) ---
                Text(
                    appViewModel.getString("Apariencia", "Appearance"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(appViewModel.getString("Modo oscuro", "Dark Mode"), fontWeight = FontWeight.Medium)
                            Text(
                                appViewModel.getString("Activa el tema oscuro", "Enable dark theme"),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        // 🟢 Este switch SÍ controla el tema global
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { appViewModel.setDarkTheme(it) }
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // --- 2️⃣ IDIOMA ---
                Text(
                    appViewModel.getString("Idioma", "Language"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { appViewModel.toggleLanguage() },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(appViewModel.getString("Idioma", "Language"), fontWeight = FontWeight.Medium)
                            Text(
                                appViewModel.getString(
                                    "Selecciona tu idioma preferido",
                                    "Select your preferred language"
                                ),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = if (isSpanish) "Español" else "English",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // --- 3️⃣ ACERCA DE ---
                Text(
                    appViewModel.getString("Acerca de", "About"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("MetroLima GO", fontWeight = FontWeight.Medium)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            appViewModel.getString(
                                "Aplicación de planificación de rutas del Metro de Lima",
                                "Route planning app for the Lima Metro"
                            ),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(appViewModel.getString("Versión", "Version"), fontWeight = FontWeight.Medium)
                            Text("1.0.0", fontWeight = FontWeight.Bold)
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Text(appViewModel.getString("Créditos del Proyecto", "Project Credits"), fontWeight = FontWeight.Medium)
                        Text(
                            "Tecsup - Tecnología de la Producción",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            appViewModel.getString(
                                "Desarrollado por el equipo de Tecsup",
                                "Developed by the Tecsup team"
                            ),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // 4. Espacio al final del contenido para evitar que el botón flotante
                // cubra los elementos inferiores cuando el scroll está al final.
                Spacer(Modifier.height(100.dp))
            }

            // 5. BOTÓN "CERRAR SESIÓN" ANCLADO ABAJO (Overlap del Box)
            Button(
                onClick = {
                    Log.d("ConfiguracionScreen", "Botón Cerrar Sesión Presionado. Redirigiendo a Login.")

                    // 🔴 LÓGICA DE CIERRE DE SESIÓN Y REDIRECCIÓN A LOGIN
                    // 1. Llama a la función de cierre de sesión (descomentado)
                    // appViewModel.logout() // Descomentar si implementas esta función

                    // 2. Navega al login y limpia la pila de navegación
                    navController.navigate("login") {
                        // Esto elimina todas las pantallas anteriores a "login"
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp) // Añade padding inferior para separarlo del borde
                    .align(Alignment.BottomCenter), // ⬅️ ANCLAJE AL FONDO DEL BOX
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)) // Rojo fuerte y llamativo
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.ExitToApp,
                        contentDescription = appViewModel.getString("Cerrar Sesión", "Log Out"),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(appViewModel.getString("Cerrar Sesión", "Log Out"), fontSize = 16.sp)
                }
            }
        }
    }
}
