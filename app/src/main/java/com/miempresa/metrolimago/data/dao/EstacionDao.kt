package com.miempresa.metrolimago.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miempresa.metrolimago.model.Estacion
import kotlinx.coroutines.flow.Flow

@Dao
interface EstacionDao {

    @Query("SELECT * FROM estaciones")
    fun obtenerEstaciones(): Flow<List<Estacion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEstaciones(vararg estaciones: Estacion)

    @Query("SELECT * FROM estaciones WHERE nombre LIKE :nombre")
    fun buscarPorNombre(nombre: String): Flow<List<Estacion>>
}