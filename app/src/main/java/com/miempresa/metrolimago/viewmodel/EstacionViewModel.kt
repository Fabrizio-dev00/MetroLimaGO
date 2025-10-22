package com.miempresa.metrolimago.viewmodel

import androidx.lifecycle.*
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.repository.EstacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import android.util.Log

class EstacionViewModel(private val repository: EstacionRepository) : ViewModel() {


    private val _filtro = MutableStateFlow("")
    val filtro: StateFlow<String> = _filtro

    val estaciones = _filtro.flatMapLatest { nombre ->
        if (nombre.isEmpty()) repository.obtenerEstaciones()
        else repository.buscarPorNombre(nombre)
    }


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje


    fun insertar(estacion: Estacion) = viewModelScope.launch {
        repository.insertar(estacion)
    }

    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }

    fun insertarEjemplo() = viewModelScope.launch {
        repository.insertarEjemplo()
    }


    fun cargarDesdeAPI() = viewModelScope.launch {
        _isLoading.value = true
        try {
            val estacionesRemotas = repository.obtenerEstacionesRemotas()
            estacionesRemotas.forEach { remota ->
                val estacionLocal = Estacion(
                    nombre = remota.nombre,
                    linea = remota.linea,
                    distrito = remota.distrito
                )
                repository.insertar(estacionLocal)
                Log.d("EstacionViewModel", "Insertada: ${remota.nombre}")
            }
            _mensaje.value = "Sincronización completada (${estacionesRemotas.size} estaciones)"
        } catch (e: Exception) {
            Log.e("EstacionViewModel", "Error al cargar desde API", e)
            _mensaje.value = "Error: ${e.message}"
        } finally {
            _isLoading.value = false
        }
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
