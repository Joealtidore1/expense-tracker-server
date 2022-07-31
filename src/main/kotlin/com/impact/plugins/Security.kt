package com.impact.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.impact.feature.verification.repository.AuthenticationRepository
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    authentication {
            jwt {
                val config = HoconApplicationConfig(ConfigFactory.load())
                val jwtAudience = config.property("jwt.audience").getString()
                val secret = config.property("jwt.secret").getString()
                realm = config.property("jwt.realm").getString()
                verifier(
                    JWT
                        .require(Algorithm.HMAC256(secret))
                        .withAudience(jwtAudience)
                        .withIssuer(this@configureSecurity.environment.config.property("jwt.domain").getString())
                        .build()
                )
                validate { credential ->
                    if (credential.payload.audience.contains(jwtAudience)){
                        val verify: AuthenticationRepository by inject()
                        val userId = credential.payload.getClaim("userId").asString()
                        val res = verify.verifyUserId(userId)
                        if(res) {
                            JWTPrincipal(credential.payload)
                        }else{
                            null
                        }
                    }else {
                        null
                    }
                }
            }
        }

}
