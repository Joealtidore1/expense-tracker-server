package com.impact.feature.funds.domain.services

import com.impact.feature.funds.domain.model.UpdateResponse
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*

object DeleteFundingService: FundService() {

    suspend fun deleteFunding(fundingId: Long?, userId: String): Resource<Response>{
        fundingId?.apply {
            val res = repo.deleteFunding(fundingId, userId)
            return Resource.Success(
                UpdateResponse(
                    status = true,
                    data = UpdateResponse.Data(
                        data = res > 0
                    )
                )
            )
        }

        return Resource.Failure(
            data = ResponseFailure(
                status = false,
                message = "Invalid fundingId (fundingId must be a number)"
            ),
            statusCode = HttpStatusCode.BadRequest
        )

    }
}