package com.impact.plugins

import com.impact.utility.response.ErrorResponse
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.SerializationException
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import java.sql.SQLIntegrityConstraintViolationException
import java.sql.SQLSyntaxErrorException

fun Application.configureStatusPages(){
    install(StatusPages){
        exception<AuthenticationException> { call, cause ->
            val reason = cause.message
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(
                    status = false,
                    message = reason
                )
            )
        }

        exception<SQLIntegrityConstraintViolationException> { call, cause->
            var reason = cause.message?:""
            if(reason.contains("Duplicate entry")){
                reason = "Duplicate entry"
            }
            call.respond(
                HttpStatusCode.Conflict,
                ErrorResponse(
                    status = false,
                    message = reason
                )
            )
        }

        exception<SQLSyntaxErrorException>{call, cause->
            call.respond(
                HttpStatusCode.InternalServerError,
                ResponseFailure(
                    status = false,
                    message = cause.message?:""
                )
            )
        }

        exception<IllegalStateException>{call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ResponseFailure(
                    status = false,
                    message = cause.message?:""
                )
            )
        }

        exception<AuthorizationException> { call, cause ->
            val reason = cause.message
            call.respond(
                HttpStatusCode.Forbidden,
                ErrorResponse(
                    status = false,
                    message = reason
                )
            )
        }

        status(HttpStatusCode.Unauthorized){call, _ ->
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(
                    status = false,
                    message = "Authorization failed"
                )
            )
        }

        status(HttpStatusCode.Forbidden){ call, _ ->
            call.respond(
                HttpStatusCode.Forbidden,
                ErrorResponse(
                    status = false,
                    message = "Authentication failed. Invalid authentication token"
                )
            )
        }

        exception<ConstraintViolationException> {call, cause ->
            var reason = cause.constraintViolations
                .mapToMessage(baseName = "messages")
                .map { "${it.property} ${it.message}" }[0].replace("blank", "empty")
            if(reason.contains("is required")){
                reason = reason.substring(0, reason.lastIndexOf("required"))
            }
            if(reason.contains("Must be in ")){
                reason = reason.replace("Must be in ", "must be in [") + "]"
            }
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    status = false,
                    message = reason
                )
            )
        }
        exception<SerializationException> {call, cause->
            cause.printStackTrace()
            val extra = "\nUse 'ignoreUnknownKeys = true'"
            var reason = cause.message
            if(reason?.contains(extra) == true){
                reason = "${reason!!.substring(reason!!.indexOf("'"), reason!!.indexOf("."))} is not allowed"
            }else if(reason?.contains("com.impact.") == true){
                val sub = reason!!.substring(0, reason!!.indexOf("required")) + "required"
                reason = sub.replace("Field", "")
            }else if(reason?.contains("Failed to parse type 'double' for input") == true){
                var sub = reason!!.substring(reason!!.indexOf("\"")+1)
                sub = sub.substring(0, sub.indexOf("\""))
                reason = "$sub requires floating point data"
            }
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    status = false,
                    message = "$reason"
                )
            )
        }

        status(HttpStatusCode.NotFound){call, _ ->
            call.respond(
                HttpStatusCode.NotFound,
                ResponseFailure(false, "Not found"))
        }

        status(HttpStatusCode.UnsupportedMediaType){call, _ ->
            call.respond(
                HttpStatusCode.UnsupportedMediaType,
                ErrorResponse(
                    status = false,
                    message = "Unsupported media type"
                )
            )
        }

        status(HttpStatusCode.InternalServerError){call, _ ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    status = false,
                    message = "Internal Server error"
                )
            )
        }
    }
}