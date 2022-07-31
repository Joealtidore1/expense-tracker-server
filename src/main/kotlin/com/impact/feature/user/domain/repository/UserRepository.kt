package com.impact.feature.user.domain.repository

import com.impact.utility.response.Response
import com.impact.feature.user.domain.model.payload.Login
import com.impact.feature.user.domain.model.payload.UserPayload

interface UserRepository {
    suspend fun register(user: UserPayload): Int

    suspend fun loginWithId(user: Int): Response

    suspend fun loginWithPhone(credentials: Login): Response

    suspend fun loginWithEmail(email: String): Response?

    suspend fun loginWithPhone(phone: String): Response?

    suspend fun update(user: UserPayload): Boolean
}