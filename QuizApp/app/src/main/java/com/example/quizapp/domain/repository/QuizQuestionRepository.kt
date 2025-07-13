package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.QuizQuestion

interface QuizQuestionRepository {
    suspend fun getQuizQuestions() : List<QuizQuestion>?
}