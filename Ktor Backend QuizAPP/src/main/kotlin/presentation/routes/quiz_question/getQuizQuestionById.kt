package com.example.presentation.routes.quiz_question

import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.getQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
) {
    get<QuestionRoutesPath.ById> { path ->

        quizQuestionRepository.getQuestionById(path.questionId)
            .onSuccess { question ->
                call.respond(
                    message = question,
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)

            }

    }
}