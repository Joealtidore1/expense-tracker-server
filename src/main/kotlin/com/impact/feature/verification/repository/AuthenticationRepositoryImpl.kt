package com.impact.feature.verification.repository

import com.impact.database.DatabaseManager
import com.impact.feature.user.data.local.UserEntity
import org.ktorm.dsl.eq
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf

class AuthenticationRepositoryImpl: AuthenticationRepository {
    val db = DatabaseManager.dbConnection
    override suspend fun verifyUserId(userId: String): Boolean {
        return db.sequenceOf(UserEntity).firstOrNull(){
            it.userId eq userId
        } != null
    }
}