package com.miempresa.metrolimago.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miempresa.metrolimago.model.Estacion
import com.miempresa.metrolimago.model.RutaFavorita
import com.miempresa.metrolimago.repository.EstacionRepository
import com.miempresa.metrolimago.ui.screens.ResultadoRuta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.abs

class EstacionViewModel(private val repository: EstacionRepository) : ViewModel() {

    private val _filtro = MutableStateFlow("")
    val filtro: StateFlow<String> = _filtro.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // --- ¡LA CORRECCIÓN CLAVE ESTÁ AQUÍ! ---
    // El bloque init se ejecuta UNA SOLA VEZ cuando el ViewModel es creado.
    init {
        cargarDesdeAPI()
    }
    // --- FIN DE LA CORRECCIÓN ---

    @OptIn(ExperimentalCoroutinesApi::class)
    val estaciones: StateFlow<List<Estacion>> = _filtro
        .flatMapLatest { nombre ->
            if (nombre.isEmpty()) repository.obtenerEstaciones()
            else repository.buscarPorNombre(nombre)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rutasFavoritas: StateFlow<List<RutaFavorita>> = repository.rutasFavoritas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun guardarRutaFavorita(resultado: ResultadoRuta) {
        viewModelScope.launch(Dispatchers.IO) {
            val nuevaRutaFavorita = RutaFavorita(
                nombreOrigen = resultado.origen,
                nombreDestino = resultado.destino,
                tiempoEstimadoMin = resultado.tiempoEstimadoMin,
                distanciaKm = resultado.distanciaKm,
                numeroParadas = resultado.numeroParadas
            )
            repository.insertarRutaFavorita(nuevaRutaFavorita)
            Log.d("EstacionViewModel", "Ruta favorita guardada: ${resultado.origen} -> ${resultado.destino}")
        }
    }

    fun eliminarRutaFavorita(rutaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarRutaFavorita(rutaId)
            Log.d("EstacionViewModel", "Ruta favorita eliminada con ID: $rutaId")
        }
    }

    fun insertar(estacion: Estacion) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertar(estacion)
    }

    fun setFiltro(nombre: String) {
        _filtro.value = nombre
    }

    internal fun cargarDesdeAPI() { // Lo hacemos privado porque ya no se llama desde fuera
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _error.value = null
            try {
                // Verificar si la base de datos ya tiene datos para no llamar a la API cada vez
                val cuentaLocal = repository.contarEstaciones()
                if (cuentaLocal == 0) {
                    Log.d("EstacionViewModel", "Base de datos vacía, cargando desde API...")
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
                        _error.value = "La API devolvió una lista vacía."
                        Log.w("EstacionViewModel", "La API no devolvió estaciones.")
                    }
                } else {
                    Log.d("EstacionViewModel", "La base de datos ya contiene datos ($cuentaLocal estaciones). No se carga desde la API.")
                }
            } catch (e: Exception) {
                Log.e("EstacionViewModel", "Error al cargar estaciones: ${e.message}", e)
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun calcularRuta(nombreOrigen: String, nombreDestino: String, estaciones: List<Estacion>): ResultadoRuta? {
        val estacionOrigen = estaciones.find { it.nombre == nombreOrigen }
        val estacionDestino = estaciones.find { it.nombre == nombreDestino }

        if (estacionOrigen == null || estacionDestino == null) return null

        val origenIndex = estaciones.indexOf(estacionOrigen)
        val destinoIndex = estaciones.indexOf(estacionDestino)

        val numeroDeEstaciones = abs(destinoIndex - origenIndex)
        val paradas = if (origenIndex < destinoIndex) {
            estaciones.slice(origenIndex..destinoIndex)
        } else {
            estaciones.slice(destinoIndex..origenIndex).reversed()
        }

        return ResultadoRuta(
            origen = estacionOrigen.nombre,
            destino = estacionDestino.nombre,
            numeroParadas = numeroDeEstaciones,
            tiempoEstimadoMin = numeroDeEstaciones * 3,
            distanciaKm = numeroDeEstaciones * 1.2,
            estacionesIntermedias = paradas.map { it.nombre },
            descripcion = "Viaje en Línea 1, conectando ${estacionOrigen.distrito} con ${estacionDestino.distrito}."
        )
    }

    class Factory(private val repository: EstacionRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EstacionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EstacionViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
