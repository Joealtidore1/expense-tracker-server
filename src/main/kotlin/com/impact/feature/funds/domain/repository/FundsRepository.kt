package com.impact.feature.funds.domain.repository

import com.impact.feature.funds.domain.model.FundData
import com.impact.feature.funds.domain.model.payload.FundsPayload
import com.impact.utility.response.Resource
import com.impact.utility.response.Response

interface FundsRepository {
    suspend fun addFunds(fundsPayload: FundsPayload): Int

    suspend fun getFunds(userId: String): List<FundData.Data>

    suspend fun getFunds(month: String?, year: Long?, userId: String): List<FundData.Data>

    suspend fun getBalance(userId: String): Double

    suspend fun updateFunding(month: String?, amount: Double?, year: Long?, fundingId: Long?, userId: String): Int

    suspend fun deleteFunding(fundingId: Long, userId: String): Int

    suspend fun getFundingDate(id: Int): String

    suspend fun verifyFundingId(fundingId: Long?, userId: String): Boolean
}