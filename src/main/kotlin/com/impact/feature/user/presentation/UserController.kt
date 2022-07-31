package com.impact.feature.user.presentation

import com.impact.feature.user.domain.model.payload.Login
import com.impact.feature.user.domain.model.payload.UserPayload
import com.impact.feature.user.domain.services.LoginService
import com.impact.feature.user.domain.services.RegisterService
import com.impact.feature.user.domain.services.UpdateService
import com.impact.utility.response.Resource
import com.impact.utility.response.Response

class UserController {

    private val loginService = LoginService
    private val registerService = RegisterService
    private val updateService = UpdateService

    suspend fun login(credentials: Login): Resource<Response> {
        return loginService.login(credentials)
    }

    suspend fun register(payload: UserPayload): Resource<Response>{
        return registerService.register(payload)
    }

    suspend fun updateUser(payload: UserPayload): Resource<Response> {
        return updateService.updateUser(payload)
    }
}