package com.miempresa.metrolimago.repository

import com.miempresa.metrolimago.data.dao.EstacionDao
import com.miempresa.metrolimago.model.Estacion
import kotlinx.coroutines.flow.Flow
import com.miempresa.metrolimago.data.network.RetrofitInstance
import com.miempresa.metrolimago.model.EstacionRemota

class EstacionRepository(private val estacionDao: EstacionDao) {

    fun obtenerEstaciones(): Flow<List<Estacion>> = estacionDao.obtenerEstaciones()

    suspend fun insertar(estacion: Estacion) {
        estacionDao.insertarEstaciones(estacion)
    }

    suspend fun insertarEjemplo() {
        val estaciones = listOf(
            Estacion(nombre = "Villa El Salvador", linea = "1", distrito = "Villa El Salvador"),
            Estacion(nombre = "Gamarra", linea = "1", distrito = "La Victoria"),
            Estacion(nombre = "Bayóvar", linea = "1", distrito = "San Juan de Lurigancho")
        )
        estaciones.forEach { estacionDao.insertarEstaciones(it) }
    }

    fun buscarPorNombre(nombre: String): Flow<List<Estacion>> =
        estacionDao.buscarPorNombre("%$nombre%")

    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota> {
        return RetrofitInstance.api.obtenerEstacionesRemotas()
    }
}
