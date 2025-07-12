package com.example.quizapp.presentation.issue_report

import com.example.quizapp.domain.model.QuizQuestion

data class IssueReportState(
    val quizQuestion: QuizQuestion? = null,
    val isQuestionCardExpanded: Boolean = false
)
