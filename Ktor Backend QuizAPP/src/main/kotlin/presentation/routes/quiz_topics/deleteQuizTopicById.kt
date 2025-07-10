package com.example.presentation.routes.quiz_topics

import com.example.domain.repository.QuizTopicRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.deleteQuizTopicById(
    quizTopicRepository: QuizTopicRepository
) {
    delete<QuizTopicRoutesPath.ById> { path ->
        quizTopicRepository.deleteTopicById(path.topicId)
            .onSuccess {
                call.respond(
                    HttpStatusCode.NoContent
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

}