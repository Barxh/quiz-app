package com.example.quizapp.presentation.dashboard

import com.example.quizapp.domain.model.QuizTopic

data class DashboardState(
    val userName: String = "Android Developer",
    val questionAnswers : Int = 0,
    val correctAnswers: Int = 0,
    val quizTopics: List<QuizTopic> = emptyList(),
    val isLoading : Boolean = false,
    val errorMessage: String? = null,
    val isNameEditDialogOpen: Boolean = false,
    val usernameTextFieldValue: String = "",
    val usernameError: String? = null

)
