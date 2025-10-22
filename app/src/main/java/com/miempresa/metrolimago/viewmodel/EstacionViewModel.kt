package com.miempresa.metrolimago.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.repository.EstacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EstacionViewModel(private val repository: EstacionRepository) : ViewModel() {

    private val _filtro = MutableStateFlow("")
    val filtro: StateFlow<String> = _filtro

    // PROPIEDAD AÑADIDA PARA CONTROLAR EL ESTADO DE CARGA
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    // ----------------------------------------------------

    // Lista de estaciones locales desde Room
    val estaciones = _filtro.flatMapLatest { nombre ->
        if (nombre.isEmpty()) repository.obtenerEstaciones()
        else repository.buscarPorNombre(nombre)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Lista para mostrar datos remotos temporalmente (opcional)
    private val _estacionesRemoto = MutableStateFlow<List<Estacion>>(emptyList())
    val estacionesRemoto: StateFlow<List<Estacion>> = _estacionesRemoto

    // Insertar una estación manualmente
    fun insertar(estacion: Estacion) = viewModelScope.launch {
        repository.insertar(estacion)
    }

    // Cambiar el filtro de búsqueda
    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }

    // Insertar datos de ejemplo (para pruebas)
    fun insertarEjemplo() = viewModelScope.launch {
        repository.insertarEjemplo()
    }

    // Función principal con control de estado de carga
    fun cargarDesdeAPI() = viewModelScope.launch {
        _isLoading.value = true // 1. Empezar carga
        try {
            // 1️⃣ Obtener datos remotos desde la API (List<EstacionRemota>)
            val estacionesRemotas = repository.obtenerEstacionesRemotas()

            // 2️⃣ Convertirlas a List<Estacion>
            val estacionesLocales = estacionesRemotas.map { remota ->
                Estacion(
                    nombre = remota.nombre,
                    linea = remota.linea,
                    distrito = remota.distrito
                )
            }

            // 3️⃣ Guardarlas en Room
            estacionesLocales.forEach { estacion ->
                repository.insertar(estacion)
            }

            // 4️⃣ Actualizar el flujo de estaciones remotas (opcional)
            _estacionesRemoto.value = estacionesLocales

        } catch (e: Exception) {
            e.printStackTrace()
            // Aquí podrías añadir lógica para mostrar un error en la UI si es necesario
        } finally {
            _isLoading.value = false // 2. Finalizar carga (siempre se ejecuta)
        }
    }


    // Factory para crear el ViewModel
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