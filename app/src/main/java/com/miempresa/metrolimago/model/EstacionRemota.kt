package com.miempresa.metrolimago.model

// En tu archivo model/EstacionRemota.kt

import com.google.gson.annotations.SerializedName

data class EstacionRemota(
    // Usa @SerializedName por si los nombres de las variables
    // no coinciden exactamente con el JSON.
    @SerializedName("id") val id: String, // El ID en MockAPI a veces es String
    @SerializedName("nombre") val nombre: String,
    @SerializedName("linea") val linea: String,
    @SerializedName("distrito") val distrito: String,
    @SerializedName("horario") val horario: String, // <-- Campo que faltaba
    @SerializedName("alerta") val alerta: String    // <-- Campo que faltaba
)

