package com.impact

import com.impact.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit =
    EngineMain.main(args)

fun Application.module() {
    configureStatusPages()
    configureDependencyInjection()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    //configureSecurity()

}
