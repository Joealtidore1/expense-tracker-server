package com.impact.feature.expense.domain.model

import com.impact.utility.response.Response

@kotlinx.serialization.Serializable
data class Expense(
    val status: Boolean,
    val data: ExpenseData
): Response()
