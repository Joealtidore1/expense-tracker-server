package com.impact.feature.funds.domain.model.payload

import com.impact.feature.user.domain.model.payload.uuidRegex
import com.impact.utility.Utils.Companion.months
import com.impact.utility.Utils.Companion.sdf
import org.valiktor.functions.*
import org.valiktor.validate
import java.text.SimpleDateFormat
import java.util.Calendar

@kotlinx.serialization.Serializable
data class FundsPayload(
    val month: String? = null,
    var userId: String? = null,
    val fundingId: Long,
    val amount: Double,
    val year: Int = sdf.format(Calendar.getInstance().time).toInt()
){
    init {
        validate(this){
            validate(FundsPayload::amount).isNotNull().isGreaterThan(0.0)
            validate(FundsPayload::fundingId).isNotNull().isPositive()
            if(month != null){
                validate(FundsPayload::month)
                    .isNotBlank()
                    .isIn(
                        months
                    )
            }
        }
    }
}


