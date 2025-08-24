package com.example.quizapp.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.QuizQuestionRepositoryImpl
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.model.UserAnswer
import com.example.quizapp.domain.repository.QuizQuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel: ViewModel() {
    private val repository: QuizQuestionRepository = QuizQuestionRepositoryImpl()

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
            val quizQuestions = repository.getQuizQuestions()
            if(quizQuestions != null){
                _state.update { it.copy(question = quizQuestions) }
            }

        }
    }
}