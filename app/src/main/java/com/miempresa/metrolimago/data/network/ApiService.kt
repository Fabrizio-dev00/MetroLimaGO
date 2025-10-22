package com.miempresa.metrolimago.data.network

import retrofit2.http.GET
import com.miempresa.metrolimago.model.EstacionRemota

interface ApiService {
    @GET("47a2b4b8-97c3-4d5d-a4af-839f37a2a9e2")
    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota>
}
