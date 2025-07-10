package com.example.presentation.routes.quiz_question

import com.example.domain.model.QuizQuestion
import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.upsertQuizQuestion(
    quizQuestionRepository: QuizQuestionRepository
) {
    post <QuestionRoutesPath>{
        val question = call.receive<QuizQuestion>()
        quizQuestionRepository.upsertQuestion(question)
            .onSuccess {
                call.respond(
                    message = "Question Added Successfully",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)

            }


    }
}