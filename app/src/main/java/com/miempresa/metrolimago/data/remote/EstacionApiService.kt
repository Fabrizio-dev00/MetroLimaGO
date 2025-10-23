package com.miempresa.metrolimago.data.remote

import com.miempresa.metrolimago.model.EstacionRemota
import retrofit2.http.GET

interface EstacionApiService {
    @GET("estaciones")
    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota>
}
