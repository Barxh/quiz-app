package com.example.quizapp.presentation.quiz

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quizapp.domain.model.QuizQuestion
import com.example.quizapp.domain.model.UserAnswer
import com.example.quizapp.presentation.common_component.ErrorScreen
import com.example.quizapp.presentation.quiz.component.ExitQuizDialog
import com.example.quizapp.presentation.quiz.component.QuizScreenLoadingContent
import com.example.quizapp.presentation.quiz.component.QuizScreenTopBar
import com.example.quizapp.presentation.quiz.component.QuizSubmitButtons
import com.example.quizapp.presentation.quiz.component.SubmitQuizDialog
import kotlinx.coroutines.flow.Flow

@Composable
fun QuizScreen(
    state: QuizState,
    event: Flow<QuizEvent>,
    navigateToDashboardScreen: () -> Unit,
    navigateToResultScreen: () -> Unit,
    onAction: (QuizAction) -> Unit
) {
    val context = LocalContext.current

        LaunchedEffect(key1 = Unit){
            event.collect { event ->
                when(event){
                    is QuizEvent.ShowToast ->{
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    SubmitQuizDialog(
        onDialogDismiss = {},
        onConfirmButtonClick = {},
        isOpen = state.isSubmitDialogOpen
    )
    ExitQuizDialog(
        onDialogDismiss = {}, onConfirmButtonClick = {}, isOpen = state.isExitDialogOpen
    )

    Column(modifier = Modifier.fillMaxSize()) {
        QuizScreenTopBar(
            title = state.topBarTitle, onExitQuizButtonClick = navigateToDashboardScreen
        )
        if (state.isLoading) {
            QuizScreenLoadingContent(
                modifier = Modifier.fillMaxSize(), loadingMessage = state.loadingMessage
            )
        } else {
            when {
                state.errorMessage != null -> ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    errorMessage = state.errorMessage,
                    onRefreshIconClick = {})

                state.question.isEmpty() -> ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    errorMessage = "No Quiz Question Available",
                    onRefreshIconClick = {})

                else -> {

                    QuizScreenContent(
                        state = state,
                        onSubmitButtonClick = navigateToResultScreen,
                        onAction = onAction
                    )

                }
            }
        }


    }
}

@Composable
fun QuizScreenContent(
    modifier: Modifier = Modifier,
    state: QuizState,
    onSubmitButtonClick: () -> Unit,
    onAction: (QuizAction) -> Unit
) {

    val pagerState = rememberPagerState(
        pageCount = {
            state.question.size
        }
    )
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { pageIndex ->
            onAction(QuizAction.JumpToQuestion(pageIndex))
        }
    }
    LaunchedEffect(state.currentQuestionIndex) {
        pagerState.animateScrollToPage(state.currentQuestionIndex)
    }
    Column(modifier = modifier.fillMaxSize()) {
        QuestionNavigationRow(
            questions = state.question,
            currentQuestionIndex = state.currentQuestionIndex,
            answer = state.answers,
            onTabSelected = { index ->
                onAction(QuizAction.JumpToQuestion(index))
            })
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState
        ) {
            QuestionItem(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState()),
                questions = state.question,
                currentQuestionIndex = state.currentQuestionIndex,
                answers = state.answers,
                onOptionSelected = { questionId, option ->

                    onAction(QuizAction.OnOptionSelected(questionId, option))
                }
            )
        }

        QuizSubmitButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            isPreviousButtonEnable = state.currentQuestionIndex != 0,
            isNextButtonEnable = state.currentQuestionIndex != state.question.lastIndex,
            onPreviousButtonClick = { onAction(QuizAction.PrevQuestionButtonClick) },
            onNextButtonClick = { onAction(QuizAction.NextQuestionButtonClick) },
            onSubmitButtonClick = onSubmitButtonClick
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
        modifier = modifier, selectedTabIndex = currentQuestionIndex, edgePadding = 0.dp
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
                onClick = { onTabSelected(index) }) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp), text = "${index + 1}"
                )
            }

        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
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
            text = question.question, style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(10.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            question.allOptions.forEach { option ->
                OptionItem(
                    modifier = Modifier
                        .widthIn(min = 400.dp)
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
}

@Composable
fun OptionItem(
    modifier: Modifier = Modifier, option: String, isSelected: Boolean, onClick: () -> Unit
) {
    Card(modifier = modifier
        .clickable {
            onClick()
        }
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small
        ), colors = CardDefaults.cardColors(
        containerColor = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else MaterialTheme.colorScheme.surface
    )) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected, onClick = { onClick() })
            Text(
                text = option, style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}