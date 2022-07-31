package com.impact.feature.expense.domain.services

import com.impact.feature.expense.domain.model.payload.ExpenseUpdatePayload
import com.impact.feature.funds.domain.model.UpdateResponse
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*

object UpdateExpenseService: ExpenseServices() {
    suspend fun updateExpense(payload: ExpenseUpdatePayload, userId: String, expenseId: Long?): Resource<Response>{
        payload.apply {
            return if(expenseId == null){
                Resource.Failure(
                    data = ResponseFailure(
                        status = false,
                        message = "Invalid fundingId (fundingId must be a number)"
                    ),
                    statusCode = HttpStatusCode.BadRequest
                )
        }else if(!repo.verifyExpenseId(expenseId)){
                Resource.Failure(
                    data = ResponseFailure(
                        status = false,
                        message = "Record with [expenseId: $expenseId] does not exist or has been moved"
                    ),
                    statusCode = HttpStatusCode.BadRequest
                )
        }else if(item.isNullOrBlank() && description.isNullOrBlank()
                && type.isNullOrBlank() && categoryId == null
                && amount == null
            ){
                Resource.Failure(
                    data = ResponseFailure(
                        status = false,
                        message = "At least one of " +
                                "[item, description, type, categoryId, amount] " +
                                "is expected"
                    ),
                    statusCode = HttpStatusCode.BadRequest
                )
            }else{
                val result = repo.updateExpense(
                    payload = payload,
                    userId = userId,
                    expenseId = expenseId
                )
                Resource.Success(
                    data = UpdateResponse(
                        status = true,
                        data = UpdateResponse.Data(
                            data = result > 0
                        )
                    ),
                    statusCode = HttpStatusCode.OK
                )
            }
        }
    }
}