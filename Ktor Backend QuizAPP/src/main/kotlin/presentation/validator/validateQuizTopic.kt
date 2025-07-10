package com.example.presentation.validator

import com.example.domain.model.QuizTopic
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateQuizTopic() {
    validate<QuizTopic> { quizTopic ->
        when {
            quizTopic.name.isBlank() || quizTopic.name.length < 3-> {
                ValidationResult.Invalid(
                    reason = "Topic must be at least 5 characters long"
                )
            }

            quizTopic.imageUrl.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Image URL must no be empty"
                )
            }



            quizTopic.code < 0 -> {
                ValidationResult.Invalid(
                    reason = "Topic code must be positive integer"
                )
            }

            else ->{
                ValidationResult.Valid
            }
        }
    }
}