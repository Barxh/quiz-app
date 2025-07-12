package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.dashboard.DashboardScreen
import com.example.quizapp.presentation.dashboard.DashboardState
import com.example.quizapp.presentation.navigation.NavGraph
import com.example.quizapp.presentation.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                val navController = rememberNavController()
                Scaffold { paddingValues ->
                    NavGraph(
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

