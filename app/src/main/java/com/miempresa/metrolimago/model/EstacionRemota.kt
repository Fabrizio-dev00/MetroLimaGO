package com.miempresa.metrolimago.model

data class EstacionRemota(
    val id: String,
    val nombre: String,
    val linea: String,
    val distrito: String
)

// Función de mapeo seguro
fun EstacionRemota.toEstacion(): Estacion {
    return Estacion(
        id = id.toIntOrNull() ?: 0,
        nombre = nombre,
        linea = linea,
        distrito = distrito,
        alerta = alerta,
        horario = horario
    )
}
