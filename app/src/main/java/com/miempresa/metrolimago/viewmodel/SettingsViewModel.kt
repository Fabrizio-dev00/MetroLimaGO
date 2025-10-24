package com.miempresa.metrolimago.viewmodel

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// El DataStore es único para toda la app.
private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    // --- 1. Lógica para el Modo Oscuro (ya la tenías) ---
    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")

    val isDarkMode: StateFlow<Boolean> = application.dataStore.data
        .map { preferences ->
            // El valor por defecto al instalar la app es 'false' (modo claro)
            preferences[IS_DARK_MODE] ?: false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun setDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { settings ->
                settings[IS_DARK_MODE] = isDark
            }
        }
    }

    // --- 2. Lógica para el Estado de Sesión (la nueva implementación) ---
    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: StateFlow<Boolean> = application.dataStore.data
        .map { preferences ->
            // El valor por defecto al instalar la app es 'false' (no ha iniciado sesión)
            preferences[IS_LOGGED_IN] ?: false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false // Valor inicial mientras se determina si hay sesión
        )

    fun setLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { settings ->
                settings[IS_LOGGED_IN] = isLoggedIn
            }
        }
    }


    // --- 3. Factory para crear el ViewModel (no cambia) ---
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
