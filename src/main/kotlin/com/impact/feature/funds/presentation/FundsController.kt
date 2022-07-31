package com.impact.feature.funds.presentation

import com.impact.feature.funds.domain.model.Funding
import com.impact.feature.funds.domain.model.payload.FundsPayload
import com.impact.feature.funds.domain.model.payload.FundsUpdatePayload
import com.impact.feature.funds.domain.repository.FundsRepository
import com.impact.feature.funds.domain.services.*
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import io.ktor.http.*

class FundsController{
    private val balanceService = BalanceService
    private val deleteService = DeleteFundingService
    private val addService =  AddFundService
    private val getService = GetFundsService
    private val updateService = UpdateFundingService

    suspend fun addFunds(payload: FundsPayload): Resource<Response> {
        payload.apply {
            return addService.addFunds(this)
        }
    }

    suspend fun getBalance(userId: String): Resource<Response>{
        return balanceService.getBalance(userId)
    }

    suspend fun getFunding(userId: String, params: Parameters): Resource<Funding> {
        return getService.getFunding(userId = userId, params = params)
    }

    suspend fun updateFunding(userId: String, payload: FundsUpdatePayload, fundingId: Long?): Resource<Response>{
        return payload.let {
            updateService.update(
                payload = payload,
                userId = userId,
                fundingId = fundingId
            )
        }
    }

    suspend fun deleteFunding(userId: String, fundingId: Long?): Resource<Response>{
        return deleteService.deleteFunding(
            fundingId = fundingId,
            userId = userId
        )
    }
}