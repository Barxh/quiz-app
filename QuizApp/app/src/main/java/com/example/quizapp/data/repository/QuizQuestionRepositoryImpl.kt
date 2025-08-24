package com.example.quizapp.data.repository

import com.example.quizapp.data.mapper.toQuizQuestions
import com.example.quizapp.data.remote.KtorRemoteDataSource
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.repository.QuizQuestionRepository

class QuizQuestionRepositoryImpl(
    private val remoteDataSource: KtorRemoteDataSource
): QuizQuestionRepository {

    override suspend fun getQuizQuestions(): List<QuizQuestion>? {
        val quizQuestionDto = remoteDataSource.getQuizQuestions()

        return quizQuestionDto?.toQuizQuestions()

    }
}