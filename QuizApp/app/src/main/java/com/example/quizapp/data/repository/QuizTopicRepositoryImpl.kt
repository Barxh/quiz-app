package com.example.quizapp.data.repository

import com.example.quizapp.data.local.dao.QuizTopicDao
import com.example.quizapp.data.mapper.toQuizTopics
import com.example.quizapp.data.mapper.toQuizTopicsEntities
import com.example.quizapp.data.remote.RemoteQuizDataSource
import com.example.quizapp.domain.model.QuizTopic
import com.example.quizapp.domain.repository.QuizTopicRepository

class QuizTopicRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource,
    private val topicDao: QuizTopicDao
) : QuizTopicRepository {

    override suspend fun getQuizTopic(): List<QuizTopic>? {
        val quizTopicDto = remoteDataSource.getQuizTopics()
        return if(quizTopicDto != null){
            topicDao.clearAllQuizTopics()
            topicDao.insertQuizTopics(quizTopicDto.toQuizTopicsEntities())
            quizTopicDto.toQuizTopics()
        }else{
            val cachedTopic = topicDao.getAllQuizTopics()
            if(cachedTopic.isNotEmpty()){
                cachedTopic.toQuizTopics()
            }else{
                null
            }
        }

    }
}