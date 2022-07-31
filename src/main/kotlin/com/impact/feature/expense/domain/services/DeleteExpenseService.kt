package com.impact.feature.expense.domain.services

import com.impact.feature.funds.domain.model.UpdateResponse
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*
import kotlin.math.exp

object DeleteExpenseService: ExpenseServices() {
    suspend fun delete(userId: String, expenseId: Long?): Resource<Response>{
        return if(expenseId == null){
            Resource.Failure(
                data = ResponseFailure(
                    status = false,
                    message = "Invalid expenseId (fundingId must be a number)"
                ),
                statusCode = HttpStatusCode.BadRequest
            )
        } else if(!repo.verifyExpenseId(expenseId)){
            Resource.Failure(
                data = ResponseFailure(
                    status = false,
                    message = "Record with [expenseId: $expenseId] does not exist or has been moved"
                ),
                statusCode = HttpStatusCode.BadRequest
            )
        }else{
            val res = repo.deleteExpense(userId, expenseId)
            Resource.Success(
                UpdateResponse(
                    status = true,
                    data = UpdateResponse.Data(
                        data = res > 0
                    )
                ),
                statusCode = HttpStatusCode.OK
            )
        }

    }
}