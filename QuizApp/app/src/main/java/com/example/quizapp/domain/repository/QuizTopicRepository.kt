package com.example.quizapp.domain.repository

import com.example.quizapp.domain.model.QuizTopic

interface QuizTopicRepository {
    suspend fun getQuizTopic() : List<QuizTopic>?
}