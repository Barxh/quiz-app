package com.example.quizapp.data.remote

import com.example.quizapp.data.remote.dto.QuizQuestionDto
import com.example.quizapp.data.remote.dto.QuizTopicDto
import com.example.quizapp.data.util.Constant.BASE_URL
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteDataSource(
    private val httpClient: HttpClient
): RemoteQuizDataSource {

    override suspend fun getQuizTopics(): Result<List<QuizTopicDto>, DataError> {
        return safeCall<List<QuizTopicDto>> { httpClient.get(urlString = "$BASE_URL/quiz/topics") }
    }

    override suspend fun getQuizQuestions(): Result<List<QuizQuestionDto>, DataError>{
        return safeCall <List<QuizQuestionDto>>{ httpClient.get(urlString = "$BASE_URL/quiz/questions") }
    }


}