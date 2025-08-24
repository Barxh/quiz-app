package com.example.quizapp.data.local

import android.content.Context
import androidx.room.Room
import com.example.quizapp.data.util.Constant.DATABASE_NAME

object DatabaseFactory {
    fun create(context: Context): QuizDatabase {
        return Room.databaseBuilder(
            context,
            QuizDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}