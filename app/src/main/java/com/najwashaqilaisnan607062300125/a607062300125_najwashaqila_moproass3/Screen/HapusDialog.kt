package com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.Screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun HapusDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        text = {
            Text(text = "Yakin ingin menghapus data ini?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(text = "Hapus")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = "Batal")
            }
        },
        onDismissRequest = onDismissRequest
    )
}