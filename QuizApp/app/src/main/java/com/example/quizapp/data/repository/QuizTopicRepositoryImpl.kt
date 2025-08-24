package com.example.quizapp.data.repository

import com.example.quizapp.data.mapper.toQuizTopics
import com.example.quizapp.data.remote.RemoteQuizDataSource
import com.example.quizapp.domain.model.QuizTopic
import com.example.quizapp.domain.repository.QuizTopicRepository

class QuizTopicRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource
) : QuizTopicRepository {

    override suspend fun getQuizTopic(): List<QuizTopic>? {
        val quizTopicDto = remoteDataSource.getQuizTopics()


        return quizTopicDto?.toQuizTopics()

    }
}