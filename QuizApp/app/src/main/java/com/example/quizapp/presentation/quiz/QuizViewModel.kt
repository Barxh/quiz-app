package com.example.quizapp.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.QuizQuestionRepositoryImpl
import com.example.quizapp.domain.model.QuizQuestion
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

    private fun getQuizQuestions(){
        viewModelScope.launch {
            val quizQuestions = repository.getQuizQuestions()
            if(quizQuestions != null){
                _state.update { it.copy(question = quizQuestions) }
            }

        }
    }
}