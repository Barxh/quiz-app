package com.example.quizapp.di

import com.example.quizapp.data.local.DatabaseFactory
import com.example.quizapp.data.local.QuizDatabase
import com.example.quizapp.data.remote.HttpClientFactory
import com.example.quizapp.data.remote.KtorRemoteDataSource
import com.example.quizapp.data.remote.RemoteQuizDataSource
import com.example.quizapp.data.repository.QuizQuestionRepositoryImpl
import com.example.quizapp.data.repository.QuizTopicRepositoryImpl
import com.example.quizapp.domain.repository.QuizQuestionRepository
import com.example.quizapp.domain.repository.QuizTopicRepository
import com.example.quizapp.presentation.dashboard.DashboardViewModel
import com.example.quizapp.presentation.quiz.QuizViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module{
    single { HttpClientFactory.create() }
    singleOf(::KtorRemoteDataSource).bind<RemoteQuizDataSource>()

    single{ DatabaseFactory.create(get()) }
    single{ get<QuizDatabase>().quizTopicDao()}

    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()

    viewModelOf(::QuizViewModel)
    viewModelOf(::DashboardViewModel)
}