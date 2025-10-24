package com.miempresa.metrolimago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.room.Room
import com.miempresa.metrolimago.data.MetroLimaDatabase
import com.miempresa.metrolimago.navigation.AppNavigation
import com.miempresa.metrolimago.repository.EstacionRepository
import com.miempresa.metrolimago.ui.theme.MetroLimaGOTheme
import com.miempresa.metrolimago.viewmodel.AppViewModel
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

class MainActivity : ComponentActivity() {

    // ViewModel para las estaciones (ya existente)
    private val estacionViewModel: EstacionViewModel by viewModels {
        val db = Room.databaseBuilder(
            applicationContext,
            MetroLimaDatabase::class.java,
            "metro_lima_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        val repository = EstacionRepository(db.estacionDao())
        EstacionViewModel.Factory(repository)
    }

    // 1. Instanciar el AppViewModel (necesario para tema e idioma)
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        estacionViewModel.cargarDesdeAPI()

        setContent {
            // 2. Observar el estado del tema de AppViewModel
            val isDarkTheme by appViewModel.isDarkTheme.collectAsState()

            MetroLimaGOTheme(darkTheme = isDarkTheme) {
                // 3. Pasar AMBOS ViewModels a AppNavigation
                AppNavigation(
                    viewModel = estacionViewModel,
                    appViewModel = appViewModel
                )
            }
        }
    }
}
