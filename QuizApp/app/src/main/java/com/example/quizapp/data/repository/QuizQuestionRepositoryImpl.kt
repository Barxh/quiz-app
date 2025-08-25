package com.example.quizapp.data.repository

import com.example.quizapp.data.mapper.toQuizQuestions
import com.example.quizapp.data.remote.KtorRemoteDataSource
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.repository.QuizQuestionRepository
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteDataSource: KtorRemoteDataSource
): QuizQuestionRepository {

    override suspend fun getQuizQuestions(): Result<List<QuizQuestion>, DataError> {


        return when(val result = remoteDataSource.getQuizQuestions()){
            is Result.Failure -> {
                result
            }
            is Result.Success -> {
                val questionsDto = result.data
                Result.Success(questionsDto.toQuizQuestions())
            }
        }

    }
}