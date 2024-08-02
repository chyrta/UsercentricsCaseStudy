package com.usercentrics.test.android.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConsentErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) =
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Initialization error") },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                onRetry()
            }) {
                Text("Retry")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )