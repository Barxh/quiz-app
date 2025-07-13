package com.example.quizapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.mapper.toQuizTopics
import com.example.quizapp.data.remote.HttpClientFactory
import com.example.quizapp.data.remote.KtorRemoteDataSource
import com.example.quizapp.data.repository.QuizTopicRepositoryImpl
import com.example.quizapp.domain.repository.QuizTopicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    val quizTopicRepository: QuizTopicRepository = QuizTopicRepositoryImpl()

    init {
        getQuizTopics()
    }


    private fun getQuizTopics() {
        viewModelScope.launch {
            val quizTopic = quizTopicRepository.getQuizTopic()
            if (quizTopic != null) {
                _state.update { it.copy(quizTopics = quizTopic) }
            }
        }
    }
}