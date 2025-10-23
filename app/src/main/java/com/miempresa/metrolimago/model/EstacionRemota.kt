package com.miempresa.metrolimago.model

data class EstacionRemota(
    val id: String,
    val nombre: String,
    val linea: String,
    val distrito: String,
    val horario: String,
    val alerta: String
)
