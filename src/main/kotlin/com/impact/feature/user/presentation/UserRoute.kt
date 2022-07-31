package com.impact.feature.user.presentation

import com.impact.utility.response.ResponseFailure
import com.impact.feature.user.domain.model.User
import com.impact.feature.user.domain.model.payload.Login
import com.impact.feature.user.domain.model.payload.UserPayload
import com.impact.feature.user.domain.repository.UserRepository
import com.impact.utility.response.Resource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoute(){
    val userRepo = UserController()
    route("/user"){
        post("/register") {
            val payload = call.receive<UserPayload>()
            val result = userRepo.register(payload)
            if(result is Resource.Success){
                call.respond(result.statusCode!!,  result.data as User)
            }else{
                call.respond(result.statusCode!!, result.data as ResponseFailure)
            }
        }

        post("/login") {
            val payload = call.receive<Login>()
            val result = userRepo.login(payload)
            if(result is Resource.Success){
                call.respond(HttpStatusCode.OK, result.data as User)
            }else{
                call.respond(HttpStatusCode.BadRequest, result.data as ResponseFailure)
            }
        }

        patch {

        }
    }
}