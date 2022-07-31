package com.impact.feature.expense.domain.model.payload

import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isPositive
import org.valiktor.validate
import java.math.BigDecimal
import com.impact.feature.expense.domain.model.payload.ExpenseUpdatePayload as e

@kotlinx.serialization.Serializable
data class ExpenseUpdatePayload(
    val item: String? = null,
    val description: String? = null,
    val type: String? = null,
    val categoryId: Int? = null,
    val amount: Double? = null
){
    init {
        validate(this){
            if(item != null)
                validate(e::item).isNotBlank()
            if(type != null)
                validate(e::type).isNotBlank()
            if(categoryId != null)
                validate(e::categoryId).isPositive()
            if(amount != null)
                validate(e::amount).isNotNull().isPositive()
        }
    }
}