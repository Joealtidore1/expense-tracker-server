package com.impact.feature.funds.domain.model

import com.impact.utility.response.Response

@kotlinx.serialization.Serializable
data class Balance(
    val status: Boolean,
    val data: Data
): Response(){
    @kotlinx.serialization.Serializable
    data class Data(
        val balance: Double
    )
}
