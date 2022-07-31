package com.impact.feature.expense.domain.services

import com.impact.feature.expense.data.toExpenseData
import com.impact.feature.expense.domain.model.Expense
import com.impact.feature.expense.domain.model.payload.ExpensePayload
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*

object AddExpenseServices: ExpenseServices() {
    suspend fun addExpense(payload: ExpensePayload, userId: String): Resource<Response>{

        val res = repo.addExpense(payload, userId)

        return if(res > 0){
            println("Insertion Id: $res")

            val result = repo.getExpense(res)
            Resource.Success(
                data = Expense(
                    status = true,
                    data = result.toExpenseData()
                ),
                statusCode = HttpStatusCode.OK
            )
        }else{
            Resource.Failure(
                ResponseFailure(
                    status = false,
                    message = "Could not add expense"
                ),
                statusCode = HttpStatusCode.Conflict
            )
        }
    }
}