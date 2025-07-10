package com.example.presentation.routes.quiz_topics

import com.example.domain.model.QuizTopic
import com.example.domain.repository.QuizTopicRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.upsertQuizTopic(
    quizTopicRepository: QuizTopicRepository
){
    post<QuizTopicRoutesPath>{
        val quizTopic = call.receive<QuizTopic>()
        quizTopicRepository.upsertTopic(quizTopic)
            .onSuccess {
                call.respond(
                    message = "Quiz Topic Added",
                    status = HttpStatusCode.Created
                )

            }
            .onFailure {error ->
                respondWithError(error)
            }
    }
}