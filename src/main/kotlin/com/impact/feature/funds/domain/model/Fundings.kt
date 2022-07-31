package com.impact.feature.funds.domain.model

import com.impact.utility.response.Response
import kotlinx.serialization.Serializable

@Serializable
data class Funding(
    val status: Boolean,
    val data: Data
): Response(){
    @Serializable
    data class Data(
        val count: Int,
        val rows: List<FundData.Data>
    )
}
