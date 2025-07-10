package com.example.presentation.routes.quiz_question

import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.getAllQuizQuestions(
    quizQuestionRepository: QuizQuestionRepository
) {
    get<QuestionRoutesPath>{ path ->

        quizQuestionRepository.getAllQuestions(path.topicCode, path.limit)
            .onSuccess {questions ->
                call.respond(
                    message = questions,
                    status = HttpStatusCode.OK)

            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}