package com.impact.plugins

import com.impact.feature.expense.presentation.expenseRoute
import com.impact.feature.funds.presentation.fundsRoute
import com.impact.feature.user.presentation.userRoute
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, ResponseFailure(
                status = true,
                message = "Connection to server was successful"
            ))
        }
        userRoute()
        fundsRoute()
        expenseRoute()
    }
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
