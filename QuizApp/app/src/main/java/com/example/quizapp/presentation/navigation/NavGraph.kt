package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.quizapp.presentation.dashboard.DashboardScreen
import com.example.quizapp.presentation.dashboard.DashboardState
import com.example.quizapp.presentation.issue_report.IssueReportScreen
import com.example.quizapp.presentation.issue_report.IssueReportState
import com.example.quizapp.presentation.quiz.QuizScreen
import com.example.quizapp.presentation.quiz.QuizState
import com.example.quizapp.presentation.result.ResultScreen
import com.example.quizapp.presentation.result.ResultState

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Route.DashboardScreen
    ) {
        composable<Route.DashboardScreen> {
            DashboardScreen(
                state = DashboardState(),
                onTopicCardClick = { topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen> {
            val topicCode = it.toRoute<Route.QuizScreen>().topicCode
            QuizScreen(
                state = QuizState(),
                navigateToDashboardScreen = { navController.navigateUp() },
                navigateToResultScreen = {
                    navController.navigate(Route.ResultScreen){
                        popUpTo<Route.QuizScreen>{
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Route.ResultScreen> {
            ResultScreen(
                state = ResultState(),
                onReportIconCLick = { questionId ->
                    navController.navigate(Route.IssueReportScreen(questionId))
                },
                onStartNewQuiz = {
                    navController.navigate(Route.DashboardScreen){
                        popUpTo<Route.ResultScreen>{
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Route.IssueReportScreen> {
            IssueReportScreen(
                state = IssueReportState(),
                onBackButtonClick = {navController.navigateUp()}
            )
        }
    }


}