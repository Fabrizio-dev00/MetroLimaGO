package com.miempresa.metrolimago.model

data class Ruta(
    val estaciones: List<Estacion>,
    val tiempoMinutos: Int
)