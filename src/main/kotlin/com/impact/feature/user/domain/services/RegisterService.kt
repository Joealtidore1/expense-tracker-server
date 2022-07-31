package com.impact.feature.user.domain.services

import com.impact.feature.user.domain.model.payload.UserPayload
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*

object RegisterService:UserService() {
    suspend fun register(payload: UserPayload): Resource<Response>{
        if(repo.loginWithPhone(payload.phone) != null){
            return Resource.Failure(
                ResponseFailure(
                    status = false,
                    message = "A user with this phone number already exists"
                ),
                statusCode = HttpStatusCode.Conflict
            )
        }else if(payload.email != null){
            if (repo.loginWithEmail(payload.email) != null){
                return Resource.Failure(
                    ResponseFailure(
                        status = false,
                        message = "A user with this email already exists"
                    ),
                    statusCode = HttpStatusCode.Conflict
                )
            }
        }

        val result = repo.register(payload)
            return if(result > 0){
                Resource.Success(
                    data = loginWithId(result),
                    statusCode = HttpStatusCode.Created
                )
            }else{
                Resource.Failure(
                    data = ResponseFailure(
                        status = false,
                        message = "Unable to register this user at this time"
                    ),
                    statusCode = HttpStatusCode.BadRequest
                )
            }
    }

    private suspend fun loginWithId(id: Int): Response{
        return repo.loginWithId(id)
    }
}