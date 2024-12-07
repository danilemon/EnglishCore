package com.example.englishcoreappk.Helpers

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
@Composable
fun SimpleDialog(S: String) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Cierra el diálogo si se hace clic fuera
            title = {
                Text(text = S)
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "OK")
                }
            }
        )
    }
}