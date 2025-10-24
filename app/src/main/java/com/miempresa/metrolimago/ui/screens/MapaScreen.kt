package com.miempresa.metrolimago.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun MapaScreen(navController: NavHostController) {
    val mapView = MapView(navController.context)
    LaunchedEffect(Unit) {
        mapView.onCreate(null)
        mapView.onResume()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mapa del Metro de Lima") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        AndroidView(
            factory = {
                mapView.apply {
                    getMapAsync(OnMapReadyCallback { googleMap ->
                        configurarMapa(googleMap)
                    })
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

private fun configurarMapa(map: GoogleMap) {
    val plazaMayor = LatLng(-12.0464, -77.0428)
    map.addMarker(MarkerOptions().position(plazaMayor).title("Plaza Mayor de Lima"))
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(plazaMayor, 12f))
    map.uiSettings.isZoomControlsEnabled = true
}
