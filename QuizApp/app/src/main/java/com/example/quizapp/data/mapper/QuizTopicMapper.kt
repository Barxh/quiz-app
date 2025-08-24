package com.example.quizapp.data.mapper

import com.example.quizapp.data.dto.QuizTopicDto
import com.example.quizapp.data.local.entity.QuizTopicEntity
import com.example.quizapp.data.util.Constant.BASE_URL
import com.example.quizapp.domain.model.QuizTopic

fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)
fun QuizTopicDto.toQuizTopicEntity() = QuizTopicEntity(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)
fun QuizTopicEntity.toQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = imageUrl,
    code = code
)
fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }
fun List<QuizTopicDto>.toQuizTopicsEntities() = map { it.toQuizTopicEntity() }
fun List<QuizTopicEntity>.toQuizTopics() = map { it.toQuizTopic() }