package com.miempresa.metrolimago.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutas_favoritas")
data class RutaFavorita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreOrigen: String,
    val nombreDestino: String,
    val tiempoEstimadoMin: Int,
    val distanciaKm: Double,
    val numeroParadas: Int
)
