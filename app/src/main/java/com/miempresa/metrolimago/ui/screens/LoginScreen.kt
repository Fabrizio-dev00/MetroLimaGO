package com.miempresa.metrolimago.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miempresa.metrolimago.R
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // 🟢 Lógica de Validación: Botón habilitado si ambos campos tienen contenido
    val isLoginEnabled = email.isNotBlank() && password.isNotBlank()

    // Gradiente basado en tu diseño (púrpura a azul)
    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF6C63FF), Color(0xFF3F51B5))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.height(80.dp))

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Metro Icon",
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )


            Spacer(Modifier.height(16.dp))

            Text(
                text = "Bienvenido a",
                color = Color.White,
                fontSize = 18.sp
            )
            Text(
                text = "MetroLima GO",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Planifica tus viajes en el Metro de Lima",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Default.Visibility
                            else Icons.Default.VisibilityOff

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { /* Navegar a pantalla de recuperación */ }) {
                            Text("¿Olvidaste tu contraseña?", color = Color(0xFF6C63FF))
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = { navController.navigate("home") },
                        enabled = isLoginEnabled,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
                    ) {
                        Text("Iniciar Sesión", color = Color.White)
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(start = 8.dp))
                    }

                    // Enlace Regístrate
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("¿No tienes una cuenta? ")
                        TextButton(onClick = { /* Navegar a pantalla de registro */ }) {
                            Text("Regístrate", color = Color(0xFF6C63FF), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 3. CONTINUAR COMO INVITADO
            Spacer(Modifier.height(32.dp))
            Divider(
                modifier = Modifier.padding(horizontal = 32.dp),
                color = Color.White.copy(alpha = 0.5f)
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = "o continúa con",
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.verticalGradient(
                    listOf(Color.White, Color.White)))
            ) {
                Text("Continuar como invitado", color = Color.White)
            }
        }
    }
}

