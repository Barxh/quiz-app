package com.example.quizapp.domain.model

data class QuizQuestion(
    val id: String,
    val topicCode: Int,
    val question: String,
    val allOptions: List<String>,
    val correctAnswers: String,
    val explanation: String
)
