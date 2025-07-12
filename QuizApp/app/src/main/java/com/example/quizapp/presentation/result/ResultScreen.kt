package com.example.quizapp.presentation.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.R

@Composable
fun ResultScreen(modifier: Modifier = Modifier, state: ResultState) {

    Column(modifier = modifier.fillMaxSize()) {

        Scorecard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 80.dp, horizontal = 10.dp),
            scorePercentage = state.scorePercentage,
            correctAnswerCount = state.correctAnswerCount,
            totalQuestion = state.questionsCount
        )
    }
}

@Composable
private fun Scorecard(
    modifier: Modifier = Modifier,
    scorePercentage: Int,
    correctAnswerCount: Int,
    totalQuestion: Int
){
    val resultText = when(scorePercentage){
        in 71..100 -> "Congratulations!\nGreat performance!"
        in 41..70 -> "You did well, but there's room for improvement"
        else -> "You may have struggled this time.\nMistakes are part of learning, keep going!"
    }
    val resultIconResId = when(scorePercentage){
        in 71..100 -> R.drawable.ic_laugh
        in 41..70 -> R.drawable.ic_smiley
        else -> R.drawable.ic_sad
    }
    Card(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(resultIconResId),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "You answered correctly $correctAnswerCount out of $totalQuestion.",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(10.dp),
            text = resultText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultScreen() {
    ResultScreen(
        state = ResultState(
            scorePercentage = 65
        )
    )
}