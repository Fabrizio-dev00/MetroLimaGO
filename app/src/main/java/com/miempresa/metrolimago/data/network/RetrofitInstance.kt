package com.miempresa.metrolimago.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://68fb4cf594ec960660258c22.mockapi.io/"

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 🟢 Asegúrate de que esta interfaz (ApiService) exista
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

