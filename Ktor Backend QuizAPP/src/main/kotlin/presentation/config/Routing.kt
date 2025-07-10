package com.example.presentation.config

import com.example.data.database.DatabaseFactory
import com.example.data.repository.IssueReportRepositoryImpl
import com.example.data.repository.QuizQuestionRepositoryImpl
import com.example.data.repository.QuizTopicRepositoryImpl
import com.example.domain.repository.IssueReportRepository
import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.repository.QuizTopicRepository
import com.example.presentation.routes.issue_reports.deleteIssueReportById
import com.example.presentation.routes.issue_reports.getAllIssueReports
import com.example.presentation.routes.issue_reports.insertIssueReport
import com.example.presentation.routes.quiz_question.deleteQuizQuestionById
import com.example.presentation.routes.quiz_question.getAllQuizQuestions
import com.example.presentation.routes.quiz_question.getQuizQuestionById
import com.example.presentation.routes.quiz_question.upsertQuizQuestion
import com.example.presentation.routes.quiz_topics.deleteQuizTopicById
import com.example.presentation.routes.quiz_topics.getAllQuizTopics
import com.example.presentation.routes.quiz_topics.getQuizTopicId
import com.example.presentation.routes.quiz_topics.upsertQuizTopic
import com.example.presentation.routes.root
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting(){
    install(Resources)


    val quizQuestionRepository: QuizQuestionRepository by inject ()
    val quizTopicRepository: QuizTopicRepository by inject ()
    val issueReportRepository: IssueReportRepository by inject ()

    routing {
        root()
        // Quiz Question
        getAllQuizQuestions(quizQuestionRepository)
        upsertQuizQuestion(quizQuestionRepository)
        deleteQuizQuestionById(quizQuestionRepository)
        getQuizQuestionById(quizQuestionRepository)

        //Quiz Topic
        getAllQuizTopics(quizTopicRepository)
        upsertQuizTopic(quizTopicRepository)
        deleteQuizTopicById(quizTopicRepository)
        getQuizTopicId(quizTopicRepository)

        //Issue Reports
        getAllIssueReports(issueReportRepository)
        insertIssueReport(issueReportRepository)
        deleteIssueReportById(issueReportRepository)

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}

