package com.miempresa.metrolimago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.miempresa.metrolimago.data.MetroLimaDatabase
import com.miempresa.metrolimago.navigation.AppNavigation
import com.miempresa.metrolimago.repository.EstacionRepository
import com.miempresa.metrolimago.ui.theme.MetroLimaGOTheme
import com.miempresa.metrolimago.viewmodel.EstacionViewModel

class MainActivity : ComponentActivity() {


    private val viewModel: EstacionViewModel by viewModels {
        val db = Room.databaseBuilder(
            applicationContext,
            MetroLimaDatabase::class.java,
            "metro_lima_db"
        ).build()
        val repository = EstacionRepository(db.estacionDao())
        EstacionViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.cargarDesdeAPI()

        setContent {
            MetroLimaGOTheme {

                AppNavigation(viewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetroLimaGOTheme {
        AppNavigation()
    }
}
