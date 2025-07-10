package com.example.presentation.validator

import com.example.domain.model.QuizQuestion
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateQuizQuestion() {
    validate<QuizQuestion> { quizQuestion ->
        when {
            quizQuestion.question.isBlank() || quizQuestion.question.length < 5-> {
                ValidationResult.Invalid(
                    reason = "Question must be at least 5 characters long"
                )
            }

            quizQuestion.correctAnswer.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Correct answer must no be empty"
                )
            }

            quizQuestion.incorrectAnswers.isEmpty() -> {
                ValidationResult.Invalid(
                    reason = "Incorrect answers must no be empty"
                )
            }

            quizQuestion.explanation.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Explanation must no be empty"
                )
            }

            quizQuestion.topicCode <= 0 -> {
                ValidationResult.Invalid(
                    reason = "Topic code must be a positive integer"
                )
            }
            else ->{
                ValidationResult.Valid
            }
        }
    }
}