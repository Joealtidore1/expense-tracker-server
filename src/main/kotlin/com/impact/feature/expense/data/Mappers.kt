package com.impact.feature.expense.data

import com.impact.feature.expense.data.local.DbExpenseEntity
import com.impact.feature.expense.domain.model.ExpenseData

fun DbExpenseEntity.toExpenseData() = ExpenseData(
    id = id!!,
    expenseId = expenseId,
    item = item,
    description = description,
    type = type,
    categoryId = categoryId,
    amount = amount.toDouble(),
    date = date,
    createdAt = createdAt!!,
    updateAt = updatedAt
)