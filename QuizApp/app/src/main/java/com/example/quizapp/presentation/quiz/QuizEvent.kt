package com.example.quizapp.presentation.quiz

sealed interface QuizEvent {
    data class ShowToast(val message: String): QuizEvent
}