package com.miempresa.metrolimago.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.model.RutaFavorita
import kotlinx.coroutines.flow.Flow

@Dao
interface EstacionDao {

    @Query("SELECT * FROM estaciones ORDER BY nombre ASC")
    fun obtenerEstaciones(): Flow<List<Estacion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEstaciones(estacion: Estacion)

    @Query("SELECT * FROM estaciones WHERE nombre LIKE :nombre")
    fun buscarPorNombre(nombre: String): Flow<List<Estacion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(estaciones: List<Estacion>)

    @Query("SELECT COUNT(*) FROM estaciones")
    suspend fun contarEstaciones(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRutaFavorita(ruta: RutaFavorita)

    @Query("SELECT * FROM rutas_favoritas ORDER BY id DESC")
    fun obtenerRutasFavoritas(): Flow<List<RutaFavorita>>

    @Query("DELETE FROM rutas_favoritas WHERE id = :rutaId")
    suspend fun eliminarRutaFavorita(rutaId: Int)
}
