package com.miempresa.metrolimago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.miempresa.metrolimago.navigation.AppNavigation
import com.miempresa.metrolimago.ui.theme.MetroLimaGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLimaGOTheme {
                AppNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(){
    MetroLimaGOTheme {
        AppNavigation()
    }
}
