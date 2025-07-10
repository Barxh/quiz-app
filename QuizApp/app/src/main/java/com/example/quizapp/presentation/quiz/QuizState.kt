package com.example.quizapp.presentation.quiz

import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.model.UserAnswer

data class QuizState(
    val question: List<QuizQuestion> = emptyList(),
    val answers: List<UserAnswer> = emptyList(),
    val currentQuestionIndex: Int = 0
)
