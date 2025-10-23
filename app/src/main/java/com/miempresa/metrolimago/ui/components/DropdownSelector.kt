package com.miempresa.metrolimago.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownSelector(
    label: String,
    opciones: List<String>,
    seleccionActual: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = seleccionActual,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier
                .clickable { expanded = true }
                .padding(vertical = 4.dp),
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSelect(opcion)
                        expanded = false
                    }
                )
            }
        }
    }
}