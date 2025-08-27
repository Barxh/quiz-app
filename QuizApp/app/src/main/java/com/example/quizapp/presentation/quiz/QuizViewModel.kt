package com.example.quizapp.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.quizapp.domain.model.UserAnswer
import com.example.quizapp.domain.repository.QuizQuestionRepository
import com.example.quizapp.domain.repository.QuizTopicRepository
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.onFailure
import com.example.quizapp.domain.util.onSuccess
import com.example.quizapp.presentation.navigation.Route
import com.example.quizapp.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val questionRepository: QuizQuestionRepository,
    private val topicRepository: QuizTopicRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val topicCode = savedStateHandle.toRoute<Route.QuizScreen>().topicCode

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val _event = Channel<QuizEvent>()
    val event = _event.receiveAsFlow()


    init {
        setupQuiz()
    }

    fun onAction(action: QuizAction) {
        when (action) {
            QuizAction.NextQuestionButtonClick -> {
                val newIndex = (state.value.currentQuestionIndex + 1)
                    .coerceAtMost(state.value.question.lastIndex)
                _state.update { it.copy(currentQuestionIndex = newIndex) }
            }

            QuizAction.PrevQuestionButtonClick -> {
                val newIndex = (state.value.currentQuestionIndex - 1)
                    .coerceAtLeast(0)
                _state.update { it.copy(currentQuestionIndex = newIndex) }
            }

            is QuizAction.JumpToQuestion -> {
                _state.update { it.copy(currentQuestionIndex = action.index) }
            }

            is QuizAction.OnOptionSelected -> {
                val newAnswer = UserAnswer(action.questionId, action.answer)
                val currentAnswers = state.value.answers.toMutableList()
                val existingAnswerIndex =
                    currentAnswers.indexOfFirst { it.questionId == action.questionId }
                if (existingAnswerIndex != -1) {
                    currentAnswers.removeAt(existingAnswerIndex)
                }
                currentAnswers.add(newAnswer)
                _state.update { it.copy(answers = currentAnswers) }
            }
        }
    }

    private fun setupQuiz() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    loadingMessage = "Setting up quiz..."
                )
            }
            getQuizTopicName(topicCode)
            getQuizQuestions(topicCode)
            _state.update {
                it.copy(
                    isLoading = false,
                    loadingMessage = null
                )
            }
        }
    }

    private suspend fun getQuizQuestions(topicCode: Int) {
        _state.update {
            it.copy(
                isLoading = true,
                loadingMessage = "Setting up quiz..."
            )
        }
        questionRepository.getQuizQuestions(topicCode)
            .onSuccess { quizQuestions ->
                _state.update {
                    it.copy(
                        question = quizQuestions,
                        errorMessage = null,
                    )
                }
            }.onFailure { error ->

                val errorMessage = error.getErrorMessage()
                _state.update {
                    it.copy(
                        question = emptyList(),
                        errorMessage = errorMessage,
                    )
                }
            }


    }

    private suspend fun getQuizTopicName(topicCode: Int) {


        topicRepository.getQuizTopicByCode(topicCode)
            .onSuccess { topic ->
                _state.update { it.copy(topBarTitle = topic.name + " Quiz") }
            }.onFailure { error ->
                _event.send(QuizEvent.ShowToast(error.getErrorMessage()))
            }


    }
}