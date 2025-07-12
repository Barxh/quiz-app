package com.example.quizapp.presentation.issue_report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quizapp.presentation.issue_report.component.IssueReportScreenTopBar
import com.example.quizapp.presentation.issue_report.component.QuestionCard


@Composable
fun IssueReportScreen(modifier: Modifier = Modifier, state: IssueReportState) {
    Column(modifier = modifier.fillMaxSize()) {

        IssueReportScreenTopBar(
            title = "Report an Issue",
            onBackButtonClick = {}
        )
        QuestionCard(
            question = state.quizQuestion,
            isCardExpanded = state.isQuestionCardExpanded,
            onExpandClick = {}
        )
    }
}