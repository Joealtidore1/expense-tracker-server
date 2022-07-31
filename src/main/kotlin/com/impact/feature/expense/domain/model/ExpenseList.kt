package com.impact.feature.expense.domain.model

import com.impact.utility.response.Response

@kotlinx.serialization.Serializable
data class ExpenseList(
    val status: Boolean,
    val data: Data
): Response(){
    @kotlinx.serialization.Serializable
     data class Data(
        val count: Int,
        val rows: List<ExpenseData>
     )

}