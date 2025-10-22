package com.miempresa.metrolimago.data.remote

import com.miempresa.metrolimago.model.Estacion
import retrofit2.http.GET

interface EstacionApiService {
    @GET("estaciones")
    suspend fun obtenerEstaciones(): List<Estacion>
}
