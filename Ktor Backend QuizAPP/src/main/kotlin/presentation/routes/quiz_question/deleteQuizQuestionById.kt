package com.example.presentation.routes.quiz_question

import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.delete
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.deleteQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
) {
    delete<QuestionRoutesPath.ById>{ path ->

        quizQuestionRepository.deleteQuestionById(path.questionId)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}