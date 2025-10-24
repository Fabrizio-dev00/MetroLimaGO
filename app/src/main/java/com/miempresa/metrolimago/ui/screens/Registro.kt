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
fun RegistroScreen(navController: NavHostController) {
    // Estados
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Estados para errores
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Función de validación
    fun validarCampos(): Boolean {
        var esValido = true

        // Validar nombre
        if (username.isBlank()) {
            usernameError = "El nombre es requerido"
            esValido = false
        } else if (username.length < 3) {
            usernameError = "El nombre debe tener al menos 3 caracteres"
            esValido = false
        } else {
            usernameError = null
        }

        // Validar email
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (email.isBlank()) {
            emailError = "El correo es requerido"
            esValido = false
        } else if (!emailRegex.matches(email)) {
            emailError = "Correo electrónico inválido"
            esValido = false
        } else {
            emailError = null
        }

        // Validar contraseña
        if (password.isBlank()) {
            passwordError = "La contraseña es requerida"
            esValido = false
        } else if (password.length < 6) {
            passwordError = "La contraseña debe tener al menos 6 caracteres"
            esValido = false
        } else {
            passwordError = null
        }

        return esValido
    }

    // Validación básica para habilitar botón
    val isRegistroEnabled = username.isNotBlank() && email.isNotBlank() && password.isNotBlank()

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
            // HEADER
            Spacer(Modifier.height(80.dp))

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Metro Icon",
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Crea tu cuenta en",
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

            // FORMULARIO
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
                    // Campo Nombre
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            usernameError = null // Limpiar error al escribir
                        },
                        label = { Text("Nombre Completo") },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        isError = usernameError != null,
                        supportingText = {
                            if (usernameError != null) {
                                Text(
                                    text = usernameError!!,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))

                    // Campo Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = null
                        },
                        label = { Text("Correo electrónico") },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = emailError != null,
                        supportingText = {
                            if (emailError != null) {
                                Text(
                                    text = emailError!!,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))

                    // Campo Contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = null
                        },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = passwordError != null,
                        supportingText = {
                            if (passwordError != null) {
                                Text(
                                    text = passwordError!!,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
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

                    Spacer(Modifier.height(24.dp))

                    // Botón Registrarse
                    Button(
                        onClick = {
                            if (validarCampos()) {
                                // Aquí guardarías el usuario (SharedPreferences, Room, API, etc.)
                                showSuccessDialog = true
                            }
                        },
                        enabled = isRegistroEnabled,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
                    ) {
                        Text("Registrarse", color = Color.White)
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    // Enlace Iniciar Sesión
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("¿Ya tienes una cuenta? ")
                        TextButton(onClick = { navController.navigate("login") }) {
                            Text("Inicia Sesión", color = Color(0xFF6C63FF), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Continuar como invitado
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
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.verticalGradient(listOf(Color.White, Color.White))
                )
            ) {
                Text("Continuar como invitado", color = Color.White)
            }
        }
    }

    // Diálogo de éxito
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("¡Registro exitoso!") },
            text = { Text("Tu cuenta ha sido creada correctamente.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("home") {
                            popUpTo("registro") { inclusive = true }
                        }
                    }
                ) {
                    Text("Continuar")
                }
            }
        )
    }
}