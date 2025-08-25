package com.example.quizapp.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.model.UserAnswer
import com.example.quizapp.domain.repository.QuizQuestionRepository
import com.example.quizapp.domain.util.DataError
import com.example.quizapp.domain.util.onFailure
import com.example.quizapp.domain.util.onSuccess
import com.example.quizapp.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val questionRepository: QuizQuestionRepository
): ViewModel() {


    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    init {
        getQuizQuestions()
    }

    fun onAction(action: QuizAction){
        when(action){
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

            is QuizAction.JumpToQuestion ->{
                _state.update { it.copy( currentQuestionIndex = action.index) }
            }

            is QuizAction.OnOptionSelected -> {
                val newAnswer = UserAnswer(action.questionId, action.answer)
                val currentAnswers = state.value.answers.toMutableList()
                val existingAnswerIndex = currentAnswers.indexOfFirst { it.questionId == action.questionId }
                if (existingAnswerIndex!=-1) {
                    currentAnswers.removeAt(existingAnswerIndex)
                }
                currentAnswers.add(newAnswer)
                _state.update { it.copy(answers = currentAnswers) }
            }
        }
    }
    private fun getQuizQuestions(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            questionRepository.getQuizQuestions()
                .onSuccess { quizQuestions ->
                    _state.update { it.copy(
                        question = quizQuestions,
                        errorMessage = null,
                        isLoading = false
                    ) }
                }.onFailure { error ->

                    val errorMessage = error.getErrorMessage()
                    _state.update { it.copy(
                        question = emptyList(),
                        errorMessage = errorMessage,
                        isLoading = false
                    ) }
                }

        }
    }
}