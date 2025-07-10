package com.example.quizapp.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.domain.model.QuizTopic
import com.example.quizapp.presentation.common_component.ErrorScreen
import com.example.quizapp.presentation.dashboard.component.NameEditDialog
import com.example.quizapp.presentation.dashboard.component.ShimmerEffect
import com.example.quizapp.presentation.dashboard.component.TopicCard
import com.example.quizapp.presentation.dashboard.component.UserStatisticsCard

@Composable
fun DashboardScreen(
    state: DashboardState

) {

    NameEditDialog(
        onDialogDismiss = {},
        onConfirmButtonClick = {},
        onDismissButtonClick = {},
        onTextFieldValueChange = {},
        userNameError = state.usernameError,
        textFieldValue = state.usernameTextFieldValue,
        isOpen = state.isNameEditDialogOpen
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderSection(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onEditNameClick = {

            }
        )

        QuizTopicSection(
            Modifier.fillMaxWidth(),
            quizTopicList = state.quizTopics,
            isTopicLoading = state.isLoading,
            errorMessage = state.errorMessage,
            onRefreshIconClick = {}
        )

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onEditNameClick: () -> Unit
) {
    FlowRow(modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier
            .padding(top = 40.dp, start = 10.dp, end = 10.dp)) {
            Text(
                text = "Hello",
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                Text(
                    text = state.userName,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    modifier = Modifier.offset(y = (-20).dp, x = (-10).dp),
                    onClick = onEditNameClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Name",
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

        }
        UserStatisticsCard(
            modifier = Modifier.widthIn(max = 400.dp).padding(10.dp),
            questionAttempted = state.questionAnswers,
            correctAnswers = state.correctAnswers
        )
    }


}

@Composable
private fun QuizTopicSection(
    modifier: Modifier = Modifier,
    quizTopicList: List<QuizTopic>,
    isTopicLoading: Boolean,
    errorMessage: String?,
    onRefreshIconClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "What topic do you want to improve today?",
            style = MaterialTheme.typography.titleLarge
        )

        if (errorMessage != null) {
            ErrorScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                errorMessage = errorMessage,
                onRefreshIconClick = onRefreshIconClick
            )

        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                if (isTopicLoading) {
                    items(6) {
                        ShimmerEffect(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }
                } else {
                    items(quizTopicList) { topic ->
                        TopicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            imageUrl = topic.imageUrl,
                            topicName = topic.name,
                            onClick = { TODO() },
                        )
                    }
                }

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDashboardScreen() {
    DashboardScreen(
        state = DashboardState(
            questionAnswers = 10,
            correctAnswers = 7
        )
    )
}