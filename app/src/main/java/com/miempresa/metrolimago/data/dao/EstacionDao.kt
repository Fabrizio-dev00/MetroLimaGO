package com.miempresa.metrolimago.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miempresa.metrolimago.model.Estacion

@Dao
interface EstacionDao {

    @Query("SELECT * FROM estaciones")
    suspend fun obtenerEstaciones(): List<Estacion>

    @Insert
    suspend fun insertarEstaciones(vararg estaciones: Estacion)
}