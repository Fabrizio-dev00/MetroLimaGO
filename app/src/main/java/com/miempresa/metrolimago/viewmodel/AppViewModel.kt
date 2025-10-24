package com.miempresa.metrolimago.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel para manejar estados globales de la aplicación (Idioma y Tema).
 */
class AppViewModel : ViewModel() {

    // --- Lógica de TEMA/Modo Oscuro ---

    // Estado que indica si el tema oscuro está activo (true) o no (false).
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    /**
     * Establece el estado del tema oscuro.
     */
    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }


    // --- Lógica de IDIOMA ---

    // Estado que indica si el idioma actual es Español (true) o Inglés (false).
    // isSpanish: true -> Español, false -> Inglés
    private val _isSpanish = MutableStateFlow(true)
    val isSpanish: StateFlow<Boolean> = _isSpanish

    /**
     * Alterna el idioma entre Español (true) e Inglés (false).
     */
    fun toggleLanguage() {
        _isSpanish.value = !_isSpanish.value
    }

    /**
     * Retorna la cadena de texto adecuada basada en el idioma actual.
     */
    fun getString(es: String, en: String): String {
        return if (_isSpanish.value) es else en
    }
}
