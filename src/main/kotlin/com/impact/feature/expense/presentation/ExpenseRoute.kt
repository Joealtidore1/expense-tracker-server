package com.impact.feature.expense.presentation

import com.impact.feature.expense.domain.model.Expense
import com.impact.feature.expense.domain.model.ExpenseList
import com.impact.feature.expense.domain.model.payload.ExpensePayload
import com.impact.feature.expense.domain.model.payload.ExpenseUpdatePayload
import com.impact.feature.funds.domain.model.UpdateResponse
import com.impact.utility.TokenManagerService
import com.impact.utility.response.Resource
import com.impact.utility.response.ResponseFailure
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.expenseRoute(){
    route("/expense"){
        authenticate {
            val tokenManager: TokenManagerService by inject()
            val expense = ExpenseController()
            post {
                val payload = call.receive<ExpensePayload>()
                val userId = tokenManager.verifyToken(call.principal()!!)
                when(val res = expense.addExpense(payload, userId)){
                    is Resource.Failure -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as ResponseFailure
                        )
                    }
                    is Resource.Success -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as Expense
                        )
                    }
                }
            }

            get{
                val userId = tokenManager.verifyToken(call.principal()!!)
                val params = call.request.queryParameters
                when(val res = expense.getExpense(userId, params)){
                    is Resource.Failure -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as ResponseFailure
                        )
                    }
                    is Resource.Success -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as ExpenseList
                        )
                    }
                }
            }

            put("/{expenseId}") {
                val userId = tokenManager.verifyToken(call.principal()!!)
                val expenseId = call.parameters["expenseId"]?.toLongOrNull()
                val payload = call.receive<ExpenseUpdatePayload>()
                when(val res = expense.updateExpense(expenseId = expenseId, userId = userId, payload = payload)){
                    is Resource.Failure -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as ResponseFailure
                        )
                    }
                    is Resource.Success -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as UpdateResponse
                        )
                    }
                }
            }

            delete("{expenseId}") {
                val userId = tokenManager.verifyToken(call.principal()!!)
                val expenseId = call.parameters["expenseId"]?.toLongOrNull()
                when(val res = expense.deleteExpense(expenseId = expenseId, userId = userId)){
                    is Resource.Failure -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as ResponseFailure
                        )
                    }
                    is Resource.Success -> {
                        call.respond(
                            res.statusCode!!,
                            res.data as UpdateResponse
                        )
                    }
                }
            }
        }
    }
}