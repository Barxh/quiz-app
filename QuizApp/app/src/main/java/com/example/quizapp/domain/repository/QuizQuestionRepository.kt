package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.Result

interface QuizQuestionRepository {
    suspend fun getQuizQuestions(topicCode: Int) : Result<List<QuizQuestion>, DataError>
}