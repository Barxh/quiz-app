package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.QuizTopic
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.Result

interface QuizTopicRepository {
    suspend fun getQuizTopic() : Result<List<QuizTopic>, DataError>
}