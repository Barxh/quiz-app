package com.example.quizapp.data.repository

import com.example.quizapp.data.mapper.toQuizTopics
import com.example.quizapp.data.remote.HttpClientFactory
import com.example.quizapp.data.remote.KtorRemoteDataSource
import com.example.quizapp.domain.model.QuizTopic
import com.example.quizapp.domain.repository.QuizTopicRepository

class QuizTopicRepositoryImpl : QuizTopicRepository {
    private val httpClient = HttpClientFactory.create()
    private val remoteDataSource = KtorRemoteDataSource(httpClient)
    override suspend fun getQuizTopic(): List<QuizTopic>? {
        val quizTopicDto = remoteDataSource.getQuizTopics()


        return quizTopicDto?.toQuizTopics()

    }
}