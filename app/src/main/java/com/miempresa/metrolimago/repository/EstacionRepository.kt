package com.miempresa.metrolimago.repository

import com.miempresa.metrolimago.data.dao.EstacionDao
import com.miempresa.metrolimago.model.Estacion

class EstacionRepository(private val estacionDao: EstacionDao) {

    suspend fun obtenerEstaciones(): List<Estacion> = estacionDao.obtenerEstaciones()

    suspend fun insertarEjemplo() {
        val estaciones = listOf(
            Estacion(nombre = "Villa El Salvador", linea = "1", distrito = "Villa El Salvador"),
            Estacion(nombre = "Gamarra", linea = "1", distrito = "La Victoria"),
            Estacion(nombre = "Bayóvar", linea = "1", distrito = "San Juan de Lurigancho")
        )
        estaciones.forEach { estacionDao.insertarEstaciones(it) }
    }
}
