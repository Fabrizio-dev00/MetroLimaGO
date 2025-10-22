package com.miempresa.metrolimago.viewmodel

import androidx.lifecycle.*
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.repository.EstacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class EstacionViewModel(private val repository: EstacionRepository) : ViewModel() {

    private val _filtro = MutableStateFlow("")
    val filtro: StateFlow<String> = _filtro

    // 🔹 Estado de carga (para el indicador CircularProgressIndicator)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // 🔹 Lista de estaciones (filtradas o todas)
    val estaciones = _filtro.flatMapLatest { nombre ->
        if (nombre.isEmpty()) repository.obtenerEstaciones()
        else repository.buscarPorNombre(nombre)
    }

    // 🔹 Insertar una estación
    fun insertar(estacion: Estacion) = viewModelScope.launch {
        repository.insertar(estacion)
    }

    // 🔹 Actualizar el filtro del buscador
    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }

    // 🔹 Insertar datos de ejemplo (ya existente)
    fun insertarEjemplo() = viewModelScope.launch {
        repository.insertarEjemplo()
    }

    // ✅ NUEVO: Cargar datos desde la API y guardarlos en Room
    fun cargarDesdeAPI() {
        viewModelScope.launch {
            _isLoading.value = true  // Muestra el indicador de carga
            try {
                repository.sincronizarDesdeAPI()
            } finally {
                _isLoading.value = false // Oculta el indicador cuando termina
            }
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
