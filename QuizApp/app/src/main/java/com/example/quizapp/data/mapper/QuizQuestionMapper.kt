package com.example.quizapp.data.mapper

import com.example.quizapp.data.dto.QuizQuestionDto
import com.example.quizapp.domain.model.QuizQuestion

fun QuizQuestionDto.toQuizQuestion() = QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    correctAnswer = correctAnswer,
    explanation = explanation
)

fun List<QuizQuestionDto>.toQuizQuestions() = map { it.toQuizQuestion() }