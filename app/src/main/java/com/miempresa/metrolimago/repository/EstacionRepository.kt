package com.miempresa.metrolimago.repository

import com.miempresa.metrolimago.data.dao.EstacionDao
import com.miempresa.metrolimago.model.Estacion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


data class EstacionRemota(
    val nombre: String,
    val linea: String,
    val distrito: String
)

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


    suspend fun obtenerEstacionesRemotas(): List<EstacionRemota> = withContext(Dispatchers.IO) {

        listOf(
            EstacionRemota("Atocongo", "1", "San Juan de Miraflores"),
            EstacionRemota("San Borja Sur", "1", "San Borja"),
            EstacionRemota("Cabitos", "1", "Surco"),
            EstacionRemota("La Cultura", "1", "San Borja"),
            EstacionRemota("Ayacucho", "1", "San Juan de Lurigancho")
        )
    }
}
