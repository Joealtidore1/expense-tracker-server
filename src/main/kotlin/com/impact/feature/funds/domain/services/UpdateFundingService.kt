package com.impact.feature.funds.domain.services

import com.impact.feature.funds.domain.model.UpdateResponse
import com.impact.feature.funds.domain.model.payload.FundsUpdatePayload
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*

object UpdateFundingService: FundService() {
    suspend fun update(fundingId: Long?,userId: String, payload: FundsUpdatePayload): Resource<Response>{
        payload.apply {
            if(repo.verifyFundingId(fundingId = fundingId, userId = userId)){
                if (month.isNullOrBlank() && amount == null && year == null){
                    return Resource.Failure(
                        ResponseFailure(
                            status = false,
                            message = "At least, one of [month, amount and year] is required"
                        ),
                        HttpStatusCode.BadRequest
                    )
                }else{
                    val result = repo.updateFunding(
                        month = month,
                        amount = amount,
                        year = year,
                        fundingId = fundingId,
                        userId = userId
                    )
                    return Resource.Success(
                        data = UpdateResponse(
                            status = true,
                            data = UpdateResponse.Data(
                                data = result > 0
                            )
                        )
                    )
                }
            }else {
                return Resource.Failure(
                    data = ResponseFailure(
                        status = false,
                        message = "No funding for this user with [fundingId: $fundingId]  found"
                    ),
                    statusCode = HttpStatusCode.BadRequest
                )
            }
        }

    }
}