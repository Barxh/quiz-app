package com.example.presentation.validator

import com.example.domain.model.IssueReport
import com.example.domain.model.QuizTopic
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateIssueReport() {
    validate<IssueReport> { issueReport ->
        val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        when {
            issueReport.questionId.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Question ID must not be empty"
                )
            }

            issueReport.issueType.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Issue type must not be empty"
                )
            }
            issueReport.timestamp.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Timestamp must not be empty"
                )
            }
            issueReport.additionalComment != null && issueReport.additionalComment.length < 5 -> {
                ValidationResult.Invalid(
                    reason = "Additional comment must be at least 5 characters long."
                )
            }
            issueReport.userEmail!= null && !issueReport.userEmail.matches(emailRegex) -> {
                ValidationResult.Invalid(
                    reason = "Invalid email!"
                )
            }

            else ->{
                ValidationResult.Valid
            }
        }
    }
}