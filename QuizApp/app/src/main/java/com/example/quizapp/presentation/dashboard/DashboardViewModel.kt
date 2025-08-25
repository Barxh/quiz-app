package com.example.quizapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.repository.QuizTopicRepository
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.onFailure
import com.example.quizapp.domain.util.onSuccess
import com.example.quizapp.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(private val topicRepository: QuizTopicRepository) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()


    init {
        getQuizTopics()
    }


    private fun getQuizTopics() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            topicRepository.getQuizTopic()
                .onSuccess { topics ->
                    _state.update { it.copy(
                        quizTopics = topics,
                        errorMessage = null,
                        isLoading = false
                    ) }

                }.onFailure { error ->
                    val errorMessage = error.getErrorMessage()
                    _state.update { it.copy(
                        quizTopics = emptyList(),
                        errorMessage = errorMessage,
                        isLoading = false
                    ) }
                }
        }
    }
}