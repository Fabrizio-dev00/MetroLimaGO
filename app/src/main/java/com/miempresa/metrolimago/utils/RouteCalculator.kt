package com.miempresa.metrolimago.utils

import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.model.Ruta
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object RouteCalculator {


    fun calcularRuta(origen: String, destino: String, estaciones: List<Estacion>): Ruta? {
        val listaOrdenada = estaciones.sortedBy { it.id.toInt() }

        val origenIndex = listaOrdenada.indexOfFirst { it.nombre.equals(origen, true) }
        val destinoIndex = listaOrdenada.indexOfFirst { it.nombre.equals(destino, true) }

        if (origenIndex == -1 || destinoIndex == -1) return null

        val rango = if (origenIndex <= destinoIndex)
            listaOrdenada.subList(origenIndex, destinoIndex + 1)
        else
            listaOrdenada.subList(destinoIndex, origenIndex + 1).reversed()

        val tiempo = abs(destinoIndex - origenIndex) * 2

        return Ruta(rango, tiempo)
    }
}