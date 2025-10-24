package com.miempresa.metrolimago.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel para manejar estados globales de la aplicación, como el idioma.
 * isSpanish: true -> Español, false -> Inglés
 */
class AppViewModel : ViewModel() {
    // Usamos StateFlow para que la UI reaccione a los cambios de idioma
    private val _isSpanish = MutableStateFlow(true)
    val isSpanish: StateFlow<Boolean> = _isSpanish

    /**
     * Alterna el idioma entre Español (true) e Inglés (false).
     */
    fun toggleLanguage() {
        _isSpanish.value = !_isSpanish.value
    }

    /**
     * Retorna la palabra correcta para un texto dado según el idioma actual.
     * @param es El texto en español.
     * @param en El texto en inglés.
     * @return El texto que corresponde al idioma activo.
     */
    fun getString(es: String, en: String): String {
        return if (_isSpanish.value) es else en
    }
}