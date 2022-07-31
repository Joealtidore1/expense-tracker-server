package com.impact.utility

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*

class TokenManagerServiceImpl: TokenManagerService {
    private val config = HoconApplicationConfig(ConfigFactory.load())
    private val audience = config.property("jwt.audience").getString()
    private val issuer = config.property("jwt.domain").getString()
    private val secret = config.property("jwt.secret").getString()

    override suspend fun generateToken(data: String): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", data)
            .sign(Algorithm.HMAC256(secret))
    }

    override suspend fun verifyToken(principal: JWTPrincipal): String {
        return principal.payload.getClaim("userId").asString()
    }
}