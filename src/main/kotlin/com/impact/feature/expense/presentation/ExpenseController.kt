package com.impact.feature.expense.presentation

import com.impact.feature.expense.domain.model.payload.ExpensePayload
import com.impact.feature.expense.domain.model.payload.ExpenseUpdatePayload
import com.impact.feature.expense.domain.services.AddExpenseServices
import com.impact.feature.expense.domain.services.DeleteExpenseService
import com.impact.feature.expense.domain.services.GetExpenseService
import com.impact.feature.expense.domain.services.UpdateExpenseService
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import io.ktor.http.*

class ExpenseController() {
    private val addService = AddExpenseServices
    private val getService = GetExpenseService
    private val updateService = UpdateExpenseService
    private val deleteService = DeleteExpenseService

    suspend fun addExpense(payload: ExpensePayload, userId: String): Resource<Response>{
        return addService.addExpense(payload, userId)
    }

    suspend fun getExpense(userId: String, params: Parameters): Resource<Response> {
        return getService.getExpenses(
            userId = userId,
            params = params
        )
    }

    suspend fun updateExpense(payload: ExpenseUpdatePayload, userId: String, expenseId: Long?): Resource<Response> {
        return updateService.updateExpense(
            payload = payload,
            userId = userId,
            expenseId = expenseId
        )
    }

    suspend fun deleteExpense(userId: String, expenseId: Long?): Resource<Response> {
        return deleteService.delete(
            userId = userId,
            expenseId = expenseId
        )
    }
}