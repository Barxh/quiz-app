package com.example.quizapp.presentation.dashboard.component


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameEditDialog(
    onDialogDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
    onTextFieldValueChange: (textFieldValue: String) -> Unit,
    userNameError: String?,
    textFieldValue: String,
    isOpen: Boolean,
    modifier: Modifier = Modifier,
    title: String = "Edit your name",
    confirmButtonText: String = "Rename",
    dismissButtonText: String = "Cancel"
) {
    if (isOpen) {
        AlertDialog(
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { onTextFieldValueChange(it) },
                    singleLine = true,
                    isError = userNameError != null && textFieldValue.isNotBlank(),
                    supportingText = { Text(text = userNameError.orEmpty()) }
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(text = confirmButtonText)
                }
            },
            onDismissRequest = onDialogDismiss,
            dismissButton = {
                TextButton(onClick = onDismissButtonClick) {
                    Text(text = dismissButtonText)
                }
            },
            modifier = modifier
        )
    }
}