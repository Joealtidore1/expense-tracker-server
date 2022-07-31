package com.impact.feature.user.domain.services

import com.impact.feature.funds.domain.model.UpdateResponse
import com.impact.feature.user.domain.model.payload.UserPayload
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import io.ktor.http.*

object UpdateService :UserService() {
    suspend fun updateUser(payload: UserPayload): Resource<Response>{
        val result = repo.update(payload)
        return Resource.Success(
            statusCode = HttpStatusCode.OK,
            data = UpdateResponse(
                status = true,
                data = UpdateResponse.Data(
                    data = result
                )
            )
        )
    }
}