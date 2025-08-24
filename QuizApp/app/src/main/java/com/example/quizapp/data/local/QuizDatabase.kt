package com.example.quizapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizapp.data.local.dao.QuizTopicDao
import com.example.quizapp.data.local.entity.QuizTopicEntity

@Database(
    version = 1,
    entities = [QuizTopicEntity::class]
)
abstract class QuizDatabase: RoomDatabase() {
    abstract fun quizTopicDao(): QuizTopicDao
}