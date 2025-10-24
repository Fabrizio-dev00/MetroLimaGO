package com.miempresa.metrolimago.repository

import com.miempresa.metrolimago.data.dao.EstacionDao
import com.miempresa.metrolimago.data.network.RetrofitInstance
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.model.EstacionRemota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class EstacionRepository(private val estacionDao: EstacionDao) {

    fun obtenerEstaciones(): Flow<List<Estacion>> = estacionDao.obtenerEstaciones()


    suspend fun insertar(estacion: Estacion) {
        estacionDao.insertarEstaciones(estacion)
    }


    fun buscarPorNombre(nombre: String): Flow<List<Estacion>> =
        estacionDao.buscarPorNombre("%$nombre%")


    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota> = withContext(Dispatchers.IO) {
        try {
            RetrofitInstance.api.obtenerEstacionesRemotas()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    suspend fun insertarTodas(estaciones: List<Estacion>) {
        estacionDao.insertarTodas(estaciones)
    }

    suspend fun contarEstaciones(): Int {
        return estacionDao.contarEstaciones()
    }
}
