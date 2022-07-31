package com.impact.feature.funds.domain.services

import com.impact.feature.funds.domain.model.Funding
import com.impact.utility.response.Resource
import io.ktor.http.*

object GetFundsService: FundService() {
    suspend fun getFunding(userId: String, params: Parameters): Resource.Success<Funding> {
        val month = params["month"]
        val year = params["year"]?.toLongOrNull()
        val res = if(!month.isNullOrBlank() || year != null){
            repo.getFunds(month = month, year = year, userId = userId)
        }else{
            repo.getFunds(userId)
        }

        return Resource.Success(
            statusCode = HttpStatusCode.OK,
            data =  Funding(
                status = true,
                data = Funding.Data(
                    count = res.size,
                    rows = res
                )
            )
        )
    }
}