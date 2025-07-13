package com.example.quizapp.data.mapper

import com.example.quizapp.data.dto.QuizTopicDto
import com.example.quizapp.data.util.Constant.BASE_URL
import com.example.quizapp.domain.model.QuizTopic

fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)
fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }