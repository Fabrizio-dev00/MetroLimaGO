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
import android.util.Log

class EstacionViewModel(private val repository: EstacionRepository) : ViewModel() {

    private val _filtro = MutableStateFlow("")
    val filtro: StateFlow<String> = _filtro

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    val estaciones: StateFlow<List<Estacion>> = _filtro
        .flatMapLatest { nombre ->
            if (nombre.isEmpty()) repository.obtenerEstaciones()
            else repository.buscarPorNombre(nombre)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _estacionesRemoto = MutableStateFlow<List<Estacion>>(emptyList())

    fun insertar(estacion: Estacion) = viewModelScope.launch {
        repository.insertar(estacion)
    }

    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }

    fun cargarDesdeAPI() = viewModelScope.launch {
        if (estaciones.value.isNotEmpty()) return@launch

        _isLoading.value = true
        _error.value = null

        try {
            val estacionesRemotas = repository.obtenerEstacionesRemotas()

            if (estacionesRemotas.isNotEmpty()) {
                val estacionesLocales = estacionesRemotas.map { remota ->
                    Estacion(
                        id = remota.id.toInt(),
                        nombre = remota.nombre,
                        linea = remota.linea,
                        distrito = remota.distrito
                    )
                }

                estacionesLocales.forEach { estacion ->
                    repository.insertar(estacion)
                }

            } else {
                _error.value = "La API devolvió una lista vacía. Verifique MockAPI."
            }

        } catch (e: Exception) {
            Log.e("EstacionViewModel", "Error al cargar estaciones: ${e.message}")
            _error.value = "Error de conexión: ${e.message}"
            e.printStackTrace()
        } finally {
            _isLoading.value = false
        }
    }

    fun calcularRuta(origen: String, destino: String, estaciones: List<Estacion>): Ruta? {
        return RouteCalculator.calcularRuta(origen, destino, estaciones)
    }

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