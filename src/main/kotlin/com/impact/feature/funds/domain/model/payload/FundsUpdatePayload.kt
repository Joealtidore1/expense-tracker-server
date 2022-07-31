package com.impact.feature.funds.domain.model.payload

import com.impact.utility.Utils.Companion.months
import org.valiktor.functions.isIn
import org.valiktor.validate

@kotlinx.serialization.Serializable
data class FundsUpdatePayload(
    val amount: Double? = null,
    val month: String? = null,
    val year: Long? = null,
){
    init {
        validate(this){
            if(month != null){
                validate(FundsUpdatePayload::month).isIn(months)
            }
        }
    }
}
