package com.miempresa.metrolimago.data.network

import com.miempresa.metrolimago.model.EstacionRemota
import retrofit2.http.GET

interface ApiService {
    // 🟢 ACTUALIZADO: Usamos el path completo 'estaciones/estaciones' de la nueva API.
    @GET("estaciones/estaciones")
    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota>

    // Si tienes un endpoint para rutas (aunque ahora estén mockeadas)
    // @GET("rutas")
    // suspend fun obtenerRutasRemotas(): List<Ruta>
}
