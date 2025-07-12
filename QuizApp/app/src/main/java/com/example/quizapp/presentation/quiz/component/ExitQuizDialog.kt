package com.example.quizapp.presentation.quiz.component


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
fun ExitQuizDialog(
    onDialogDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    isOpen: Boolean,
    modifier: Modifier = Modifier,
    title: String = "Exit Quiz?",
    confirmButtonText: String = "Exit",
    dismissButtonText: String = "No"
) {
    if (isOpen) {
        AlertDialog(
            title = { Text(text = title) },
            text = {
                Text(
                    text = "Are you sure you want to exit quiz? " +
                            "You want be able to continue from where you left off."
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(text = confirmButtonText)
                }
            },
            onDismissRequest = onDialogDismiss,
            dismissButton = {
                TextButton(onClick = onDialogDismiss) {
                    Text(text = dismissButtonText)
                }
            },
            modifier = modifier
        )
    }
}