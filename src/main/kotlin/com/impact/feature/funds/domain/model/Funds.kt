package com.impact.feature.funds.domain.model

import com.impact.utility.response.Response

@kotlinx.serialization.Serializable
data class Funds(
    val status:Boolean,
    val data: Data?
): Response(){
    @kotlinx.serialization.Serializable
    data class Data(
        val balance: Double,
        val lastDeposit: Double,
        val lastDepositDate: String
    )
}
