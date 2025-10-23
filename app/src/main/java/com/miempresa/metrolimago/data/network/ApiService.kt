package com.miempresa.metrolimago.data.network

import retrofit2.http.GET
import com.miempresa.metrolimago.model.EstacionRemota

interface ApiService {
    @GET("estaciones")
    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota>
}
