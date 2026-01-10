package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

data class ConfirmationDialogConfig(
    val title: String,
    val message: String,
    val confirmButtonText: String,
    val dismissButtonText: String,
    val confirmButtonColor: Color?,
)

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    config: ConfirmationDialogConfig,
    onConfirmAction: () -> Unit,
    onDismissAction: () -> Unit,
) = with(config) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissAction,
        confirmButton = {
            TextButton(
                onClick = onConfirmAction,
            ) {
                Text(
                    confirmButtonText,
                    color = confirmButtonColor ?: MaterialTheme.colorScheme.primary,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissAction,
            ) {
                Text(
                    dismissButtonText,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
    )
}
