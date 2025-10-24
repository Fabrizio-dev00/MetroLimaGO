package com.miempresa.metrolimago.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estaciones")
data class Estacion(
    @PrimaryKey val id: Int,
    val nombre: String,
    val linea: String,
    val distrito: String
)
