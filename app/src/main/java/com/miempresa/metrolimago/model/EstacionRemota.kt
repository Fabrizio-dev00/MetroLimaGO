package com.miempresa.metrolimago.model

data class EstacionRemota(
    val id: String,
    val nombre: String,
    val linea: String,
    val distrito: String,
    val horario: String = "06:00 - 22:00",  // Valor por defecto
    val alerta: String = "Sin alertas"       // Valor por defecto
)

// Función de mapeo seguro
fun EstacionRemota.toEstacion(): Estacion {
    return Estacion(
        id = id.toIntOrNull() ?: 0,
        nombre = nombre,
        linea = linea,
        distrito = distrito,
        horario = horario,  // ← Ahora sí lo incluye
        alerta = alerta     // ← Ahora sí lo incluye
    )
}