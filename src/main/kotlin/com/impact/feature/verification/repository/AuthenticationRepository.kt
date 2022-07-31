package com.impact.feature.verification.repository

interface AuthenticationRepository {
    suspend fun verifyUserId(userId: String): Boolean
}