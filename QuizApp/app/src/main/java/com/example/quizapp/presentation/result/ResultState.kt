package com.example.quizapp.presentation.result

import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.model.UserAnswer

data class ResultState(
    val scorePercentage: Int = 0,
    val correctAnswerCount: Int = 0,
    val questionsCount: Int = 0,
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val userAnswers: List<UserAnswer> = emptyList()
)
