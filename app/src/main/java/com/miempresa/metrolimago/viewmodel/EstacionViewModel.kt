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

    val estaciones = _filtro.flatMapLatest { nombre ->
        if (nombre.isEmpty()) repository.obtenerEstaciones()
        else repository.buscarPorNombre(nombre)
    }

    fun insertar(estacion: Estacion) = viewModelScope.launch {
        repository.insertar(estacion)
    }

    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }

    fun insertarEjemplo() = viewModelScope.launch {
        repository.insertarEjemplo()
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