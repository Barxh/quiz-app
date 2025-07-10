package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.quizapp.presentation.dashboard.DashboardScreen
import com.example.quizapp.presentation.dashboard.DashboardState
import com.example.quizapp.presentation.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                DashboardScreen(
                    state = DashboardState(questionAnswers = 10, correctAnswers = 7)
                )


            }
        }
    }
}

