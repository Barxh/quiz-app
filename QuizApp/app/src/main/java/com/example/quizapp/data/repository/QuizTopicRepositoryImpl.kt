package com.example.quizapp.data.repository

import com.example.quizapp.data.local.dao.QuizTopicDao
import com.example.quizapp.data.mapper.toQuizTopic
import com.example.quizapp.data.mapper.toQuizTopics
import com.example.quizapp.data.mapper.toQuizTopicsEntities
import com.example.quizapp.data.remote.RemoteQuizDataSource
import com.example.quizapp.domain.model.QuizTopic
import com.example.quizapp.domain.repository.QuizTopicRepository
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.Result

class QuizTopicRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource,
    private val topicDao: QuizTopicDao
) : QuizTopicRepository {

    override suspend fun getQuizTopic(): Result<List<QuizTopic>, DataError> {

        return when(val result = remoteDataSource.getQuizTopics()){
            is Result.Failure -> {
                val cachedTopic = topicDao.getAllQuizTopics()
                if(cachedTopic.isNotEmpty()){
                    Result.Success(cachedTopic.toQuizTopics())
                }else{
                    result
                }
            }
            is Result.Success -> {
                val quizTopicDto = result.data
                topicDao.clearAllQuizTopics()
                topicDao.insertQuizTopics(quizTopicDto.toQuizTopicsEntities())
                Result.Success(quizTopicDto.toQuizTopic())
            }
        }
    }

    override suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError> {
        return try {
            val topicEntity = topicDao.getQuizTopicByCode(topicCode)
            if (topicEntity!=null){
                Result.Success(topicEntity.toQuizTopics())
            }else{
                Result.Failure(DataError.Unknown("Quiz topic not found."))
            }

        }catch (e: Exception){
            Result.Failure(DataError.Unknown(e.message))
        }
    }
}