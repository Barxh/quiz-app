package com.example.quizapp.data.remote


import com.example.quizapp.data.remote.dto.QuizQuestionDto
import com.example.quizapp.data.remote.dto.QuizTopicDto
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.Result

interface RemoteQuizDataSource {


    suspend fun getQuizTopics(): Result<List<QuizTopicDto>, DataError>
    suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestionDto>, DataError>
}