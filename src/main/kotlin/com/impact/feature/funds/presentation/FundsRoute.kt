package com.impact.feature.funds.presentation

import com.impact.feature.funds.domain.model.Balance
import com.impact.feature.funds.domain.model.Funds
import com.impact.feature.funds.domain.model.UpdateResponse
import com.impact.feature.funds.domain.model.payload.FundsPayload
import com.impact.feature.funds.domain.model.payload.FundsUpdatePayload
import com.impact.utility.TokenManagerService
import com.impact.utility.response.Resource
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.fundsRoute(){
    route("/funds"){
        authenticate {
            val tokenManager: TokenManagerService by inject()
            val funds = FundsController()

            post {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = tokenManager.verifyToken(principal)
                val payload = call.receive<FundsPayload>()


                payload.userId = userId
                val result = funds.addFunds(payload)
                if(result is Resource.Failure){
                    call.respond(result.statusCode!!, result.data)
                }else{
                    call.respond(HttpStatusCode.Created, result.data as Funds)
                }
            }

            get("/balance") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = tokenManager.verifyToken(principal)
                val result = funds.getBalance(userId)
                if(result is Resource.Failure){
                    call.respond(result.statusCode!!, result.data)
                }else{
                    call.respond(HttpStatusCode.Created, result.data as Balance)
                }
            }

            get {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = tokenManager.verifyToken(principal)
                val params = call.request.queryParameters
                val result = funds.getFunding(userId, params)

                if(result is Resource.Failure){
                    call.respond(result.statusCode!!, result.data)
                }else{
                    call.respond(HttpStatusCode.Created, result.data)
                }
            }

            put("/{fundingId}") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = tokenManager.verifyToken(principal)
                val fundingId = call.parameters["fundingId"]?.toLongOrNull()
                val payload = call.receive<FundsUpdatePayload>()
                val response = funds.updateFunding(
                    userId = userId,
                    fundingId = fundingId,
                    payload = payload
                )

                if(response is Resource.Success){
                    call.respond(HttpStatusCode.OK, response.data as UpdateResponse)
                }else{
                    call.respond(response.statusCode!!, response.data as ResponseFailure)
                }
            }

            delete("/{fundingId}") {
                val principal = call.principal<JWTPrincipal>()!!
                val userId = tokenManager.verifyToken(principal)
                val fundingId = call.parameters["fundingId"]?.toLongOrNull()

                val response = funds.deleteFunding(userId = userId, fundingId = fundingId)
                if(response is Resource.Success){
                    call.respond(HttpStatusCode.OK, response.data as UpdateResponse)
                }else{
                    call.respond(response.statusCode!!, response.data as ResponseFailure)
                }
            }
        }
    }
}