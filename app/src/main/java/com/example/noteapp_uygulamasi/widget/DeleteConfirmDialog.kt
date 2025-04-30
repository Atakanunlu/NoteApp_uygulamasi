package com.example.noteapp_uygulamasi.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoDisturb
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.DoDisturb,
                contentDescription = "Uyarı Diyaloğu"
            )
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(text = "Evet!")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Hayır")
            }
        },
        onDismissRequest = { onDismiss() },
    )
}