package com.example.quizapp.data.remote

import com.example.quizapp.data.dto.QuizQuestionDto
import com.example.quizapp.data.dto.QuizTopicDto

interface RemoteQuizDataSource {

    suspend fun getQuizTopics(): List<QuizTopicDto>?
    suspend fun getQuizQuestions(): List<QuizQuestionDto>?
}