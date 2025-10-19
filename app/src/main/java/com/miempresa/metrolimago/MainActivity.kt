package com.miempresa.metrolimago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miempresa.metrolimago.ui.theme.MetroLimaGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaInicio()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio() {
    val gradiente = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A5AE0), Color(0xFF4FC3F7))
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F5F5)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradiente),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🚇",
                        fontSize = 36.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Bienvenido a",
                        style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                    )
                    Text(
                        text = "MetroLima GO",
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Planifica tus viajes en el Metro de Lima",
                        style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Campos de texto
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        placeholder = { Text("tucorreo@ejemplo.com") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = Color(0xFF6A5AE0),
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* acción login */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5AE0))
                    ) {
                        Text("Iniciar sesión", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("¿No tienes una cuenta? Regístrate", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Divider(thickness = 1.dp, color = Color.LightGray)

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { /* continuar invitado */ },
                        modifier = Modifier.fillMaxWidth(),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text("Continuar como invitado")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("MetroLima GO v1.0.0", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetroLimaGOTheme {
        PantallaInicio()
    }
}