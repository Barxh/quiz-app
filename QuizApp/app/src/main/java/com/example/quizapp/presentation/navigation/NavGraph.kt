package com.example.quizapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.quizapp.presentation.dashboard.DashboardScreen
import com.example.quizapp.presentation.dashboard.DashboardViewModel
import com.example.quizapp.presentation.issue_report.IssueReportScreen
import com.example.quizapp.presentation.issue_report.IssueReportState
import com.example.quizapp.presentation.quiz.QuizScreen
import com.example.quizapp.presentation.quiz.QuizViewModel
import com.example.quizapp.presentation.result.ResultScreen
import com.example.quizapp.presentation.result.ResultState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {

    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Route.DashboardScreen
    ) {
        composable<Route.DashboardScreen> {
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            DashboardScreen(
                state = state,
                onTopicCardClick = { topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen> {
            val topicCode = it.toRoute<Route.QuizScreen>().topicCode
            val viewModel = koinViewModel<QuizViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            QuizScreen(
                state = state,
                navigateToDashboardScreen = { navController.navigateUp() },
                navigateToResultScreen = {
                    navController.navigate(Route.ResultScreen) {
                        popUpTo<Route.QuizScreen> {
                            inclusive = true
                        }
                    }
                },
                onAction = viewModel::onAction
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