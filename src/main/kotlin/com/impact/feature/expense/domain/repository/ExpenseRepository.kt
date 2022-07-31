package com.impact.feature.expense.domain.repository

import com.impact.feature.expense.data.local.DbExpenseEntity
import com.impact.feature.expense.domain.model.payload.ExpensePayload
import com.impact.feature.expense.domain.model.payload.ExpenseUpdatePayload
import org.ktorm.dsl.Query

interface ExpenseRepository {
    suspend fun addExpense(payload: ExpensePayload, userId: String): Int

    suspend fun getExpense(userId: String): Query

    suspend fun getExpense(userId: String, offset: Int): Query

    suspend fun getExpense(id: Int): DbExpenseEntity

    suspend fun getExpense(userId: String, query: String): Query

    suspend fun updateExpense(payload: ExpenseUpdatePayload, expenseId: Long, userId: String): Int

    suspend fun deleteExpense(userId: String, expenseId: Long): Int

    suspend fun verifyExpenseId(expenseId: Long): Boolean

}