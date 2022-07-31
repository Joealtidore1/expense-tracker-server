package com.impact.feature.funds.domain.model

import com.impact.utility.response.Response
import kotlinx.serialization.Serializable

@Serializable
data class FundData(
    val status: Boolean,
    val data: Data
): Response(){
    @Serializable
    data class Data(
        val id: Int,
        val fundingId: Long,
        val amount: Double,
        val month: String,
        val year: Long,
        val createdAt: String,
        val updatedAt: String?
    ): Response()
}