package com.miempresa.metrolimago.data.network

import com.miempresa.metrolimago.model.EstacionRemota
import retrofit2.http.GET

interface ApiService {
    @GET("estaciones/estaciones")
    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota>
}
