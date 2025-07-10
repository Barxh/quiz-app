package com.example.presentation

import com.example.presentation.config.configureKoin
import com.example.presentation.config.configureLogging
import com.example.presentation.config.configureRouting
import com.example.presentation.config.configureSerialization
import com.example.presentation.config.configureStatusPages
import com.example.presentation.config.configureValidation
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureValidation()
    configureStatusPages()
    configureRouting()
    configureSerialization()
    configureLogging()


}
