package com.miempresa.metrolimago.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.model.Ruta
import com.miempresa.metrolimago.repository.EstacionRepository
import com.miempresa.metrolimago.utils.RouteCalculator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EstacionViewModel(private val repository: EstacionRepository) : ViewModel() {

    private val _filtro = MutableStateFlow("")
    val filtro: StateFlow<String> = _filtro

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val estaciones: StateFlow<List<Estacion>> = _filtro
        .flatMapLatest { nombre ->
            if (nombre.isEmpty()) repository.obtenerEstaciones()
            else repository.buscarPorNombre(nombre)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _estacionesRemoto = MutableStateFlow<List<Estacion>>(emptyList())
    val estacionesRemoto: StateFlow<List<Estacion>> = _estacionesRemoto


    fun insertar(estacion: Estacion) = viewModelScope.launch {
        repository.insertar(estacion)
    }

    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }


    fun cargarDesdeAPI() = viewModelScope.launch {
        _isLoading.value = true
        try {
            val estacionesRemotas = repository.obtenerEstacionesRemotas()

            val estacionesLocales = estacionesRemotas.map { remota ->
                Estacion(
                    nombre = remota.nombre,
                    linea = remota.linea,
                    distrito = remota.distrito
                )
            }

            estacionesLocales.forEach { estacion ->
                repository.insertar(estacion)
            }

            _estacionesRemoto.value = estacionesLocales

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _isLoading.value = false
        }
    }

    fun calcularRuta(origen: String, destino: String, estaciones: List<Estacion>): Ruta? {
        return RouteCalculator.calcularRuta(origen, destino, estaciones)
    }

    companion object {
        class Factory(private val repository: EstacionRepository) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(EstacionViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return EstacionViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
