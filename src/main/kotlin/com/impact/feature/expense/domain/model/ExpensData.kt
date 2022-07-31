package com.impact.feature.expense.domain.model

import com.impact.utility.response.Response

@kotlinx.serialization.Serializable
data class ExpenseData(
    val id: Int,
    val expenseId: Long,
    val item: String,
    val description: String?,
    val type: String,
    val categoryId: Int,
    val amount: Double,
    val date: String,
    val createdAt: String,
    val updateAt: String?

): Response()