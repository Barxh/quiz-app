package com.example.presentation.routes.quiz_question

import io.ktor.resources.Resource

@Resource(path = "/quiz/questions")
class QuestionRoutesPath(
    val topicCode: Int? = null,
    val limit: Int? = null
) {
    @Resource(path = "/{questionId}")
    data class  ById(
        val parent: QuestionRoutesPath = QuestionRoutesPath(),
        val questionId: String
    )
}