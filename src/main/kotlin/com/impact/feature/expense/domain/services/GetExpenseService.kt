package com.impact.feature.expense.domain.services

import com.impact.feature.expense.data.local.ExpenseEntity
import com.impact.feature.expense.domain.model.ExpenseData
import com.impact.feature.expense.domain.model.ExpenseList
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import io.ktor.http.*
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.forEach

object GetExpenseService: ExpenseServices() {
    suspend fun getExpenses(userId: String, params: Parameters): Resource<Response>{
        val expenses = mutableListOf<ExpenseData>()
        val result = if(params["offset"] != null)
            repo.getExpense(userId, params["offset"]!!)
        else if(params["query"] != null)
            repo.getExpense(userId, params["query"]!!)
        else repo.getExpense(userId)

        result.forEach {
            expenses.add(toExpenseData(it))
        }

        return Resource.Success(
            statusCode = HttpStatusCode.OK,
            data = ExpenseList(
                status = true,
                data = ExpenseList.Data(
                    count = result.totalRecords,
                    rows = expenses
                )
            )
        )
    }

    private fun toExpenseData(query: QueryRowSet) =  ExpenseData(
        id = query[ExpenseEntity.id]!!,
        expenseId = query[ExpenseEntity.expenseId]!!,
        item = query[ExpenseEntity.item]!!,
        description = query[ExpenseEntity.description],
        type = query[ExpenseEntity.type]!!,
        categoryId = query[ExpenseEntity.categoryId]!!,
        amount = query[ExpenseEntity.amount]!!.toDouble(),
        date = query[ExpenseEntity.date]!!,
        createdAt = query[ExpenseEntity.createdAt]!!,
        updateAt = query[ExpenseEntity.updatedAt]
    )
}