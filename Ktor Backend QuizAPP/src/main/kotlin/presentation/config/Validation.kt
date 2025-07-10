package com.example.presentation.config

import com.example.presentation.validator.validateIssueReport
import com.example.presentation.validator.validateQuizQuestion
import com.example.presentation.validator.validateQuizTopic
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation(){
    install(RequestValidation){
        validateQuizQuestion()
        validateQuizTopic()
        validateIssueReport()
    }
}