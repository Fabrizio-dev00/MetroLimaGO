package com.miempresa.metrolimago.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.model.Ruta
import com.miempresa.metrolimago.repository.EstacionRepository
import com.miempresa.metrolimago.utils.RouteCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EstacionViewModel(private val repository: EstacionRepository) : ViewModel() {

    private val _filtro = MutableStateFlow("")
    val filtro: StateFlow<String> = _filtro.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val estaciones: StateFlow<List<Estacion>> = _filtro
        .flatMapLatest { nombre ->
            if (nombre.isEmpty()) repository.obtenerEstaciones()
            else repository.buscarPorNombre(nombre)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun insertar(estacion: Estacion) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertar(estacion)
    }

    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }

    fun cargarDesdeAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.contarEstaciones() > 0) {
                Log.d("EstacionViewModel", "La base de datos local ya contiene datos. No se cargará desde la API.")
                return@launch
            }

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
                            distrito = remota.distrito,
                            horario = remota.horario,
                            alerta = remota.alerta
                        )
                    }
                    repository.insertarTodas(estacionesLocales)
                    Log.d("EstacionViewModel", "Se insertaron ${estacionesLocales.size} estaciones en la BD.")

                } else {
                    _error.value = "La API devolvió una lista vacía. Verifique MockAPI."
                    Log.w("EstacionViewModel", "La API no devolvió estaciones.")
                }

            } catch (e: Exception) {
                Log.e("EstacionViewModel", "Error al cargar estaciones desde la API: ${e.message}", e)
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
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
