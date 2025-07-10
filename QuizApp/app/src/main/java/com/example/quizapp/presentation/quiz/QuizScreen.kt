package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.navOptions
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.model.UserAnswer
import com.example.quizapp.presentation.quiz.component.QuizScreenTopBar

@Composable
fun QuizScreen(state: QuizState) {
    Column(modifier = Modifier.fillMaxSize()) {
        QuizScreenTopBar(
            title = "Android Quiz",
            onExitQuizButtonClick = {}
        )
        QuestionNavigationRow(
            questions = state.question,
            currentQuestionIndex = state.currentQuestionIndex,
            answer = state.answers,
            onTabSelected = {

            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        QuestionItem(
            modifier = Modifier
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            questions = state.question,
            currentQuestionIndex = state.currentQuestionIndex,
            answers = state.answers,
            onOptionSelected = { _, _ ->

            }
        )
    }
}

@Composable
private fun QuestionNavigationRow(
    modifier: Modifier = Modifier,
    questions: List<QuizQuestion>,
    currentQuestionIndex: Int,
    answer: List<UserAnswer>,
    onTabSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentQuestionIndex,
        edgePadding = 0.dp
    ) {
        questions.forEachIndexed { index, quizQuestion ->
            val containerColor = when {
                answer.any { it.questionId == quizQuestion.id } -> {
                    MaterialTheme.colorScheme.secondaryContainer
                }

                else -> MaterialTheme.colorScheme.surface
            }
            Tab(
                modifier = Modifier.background(containerColor),
                selected = currentQuestionIndex == index,
                onClick = { onTabSelected(index) }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "${index + 1}"
                )
            }

        }

    }
}

@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    questions: List<QuizQuestion>,
    currentQuestionIndex: Int,
    answers: List<UserAnswer>,
    onOptionSelected: (String, String) -> Unit
) {
    Column(modifier = modifier) {
        val question = questions[currentQuestionIndex]
        val selectedAnswer = answers.find { it.questionId == question.id }?.selectedOption
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier.height(10.dp))
        question.allOptions.forEach { option ->
            OptionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                option = option,
                isSelected = option == selectedAnswer,
                onClick = {
                    onOptionSelected(question.id, option)
                }
            )
        }
    }
}

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    option: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = false,
                onClick = { onClick() }
            )
            Text(
                text = option,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}