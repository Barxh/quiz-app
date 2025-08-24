package com.example.quizapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.data.local.entity.QuizTopicEntity


@Dao
interface QuizTopicDao {
    @Query("SELECT * FROM QUIZ_TOPICS")
    suspend fun getAllQuizTopics(): List<QuizTopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizTopics(topics: List<QuizTopicEntity>)

    @Query("DELETE FROM QUIZ_TOPICS")
    suspend fun clearAllQuizTopics()

    @Query("SELECT * FROM QUIZ_TOPICS WHERE CODE = :topicCode")
    suspend fun getQuizTopicBuCode(topicCode : Int):QuizTopicEntity

}