package com.impact.utility

import io.ktor.server.auth.jwt.*

interface TokenManagerService {
    suspend fun generateToken(data: String): String

    suspend fun verifyToken(principal: JWTPrincipal): String
}