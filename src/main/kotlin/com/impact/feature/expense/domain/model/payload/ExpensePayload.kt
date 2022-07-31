package com.impact.feature.expense.domain.model.payload

import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isPositive
import org.valiktor.validate
import com.impact.feature.expense.domain.model.payload.ExpensePayload as e

@kotlinx.serialization.Serializable
class ExpensePayload(
    val expenseId: Long,
    val item: String,
    val description: String?,
    val type: String,
    val categoryId: Int,
    val amount: Double,
    val date: String
){
    init {
        validate(this){
            validate(e::expenseId).isNotNull()
            validate(e::item).isNotNull().isNotBlank()
            validate(e::type).isNotNull().isNotBlank()
            validate(e::categoryId).isNotNull().isPositive()
            validate(e::amount).isNotNull().isPositive()
            validate(e::date).isNotNull()
        }
    }
}