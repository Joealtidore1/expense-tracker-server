package com.impact.feature.user.data.repository

import com.impact.database.DatabaseManager
import com.impact.feature.user.data.local.DbUserEntity
import com.impact.feature.user.data.local.UserDto
import com.impact.feature.user.data.local.UserEntity
import com.impact.feature.user.data.toUser
import com.impact.feature.user.data.toUserData
import com.impact.feature.user.domain.model.payload.Login
import com.impact.feature.user.domain.model.payload.UserPayload
import com.impact.feature.user.domain.repository.UserRepository
import com.impact.utility.TokenManagerService
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.entity.first
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.mindrot.jbcrypt.BCrypt

class UserRepositoryImpl(private val tokenManager: TokenManagerService) : UserRepository {
    private val db = DatabaseManager.dbConnection

    override suspend fun register(user: UserPayload): Int {
        return db.insertAndGenerateKey(UserEntity){
            set(UserEntity.phone, user.phone)
            set(UserEntity.name, user.name)
            set(UserEntity.email, user.email)
            set(UserEntity.gender, user.gender)
            set(UserEntity.userId, user.userId)
            set(UserEntity.password, user.hashedPassword())
        } as Int

    }

    override suspend fun loginWithId(user: Int): Response {
        return db.sequenceOf(UserEntity).first {
            it.id eq user
        }.toUser()
    }

    override suspend fun loginWithPhone(credentials: Login): Response {
        val result = if(credentials.email.isNullOrBlank()) {
            db.sequenceOf(UserEntity).firstOrNull {
                (it.phone eq credentials.phone!!)
            }
        }else{
            db.sequenceOf(UserEntity).firstOrNull {
                (it.email eq credentials.email)
            }
        }
        result?.let {
            if(BCrypt.checkpw(credentials.password, it.password)){
                return it.toUser()
            }
        }
        return ResponseFailure(
            status = false,
            message = "${if(credentials.email.isNullOrBlank()) "Phone" else "Email"} and/or password is incorrect"
        )

    }

    override suspend fun loginWithPhone(phone: String): Response? {
        val result = db.sequenceOf(UserEntity).firstOrNull {
            (it.phone eq phone)
        }
        return result?.toUserData()
    }

    override suspend fun loginWithEmail(email: String): Response? {
        val result = db.sequenceOf(UserEntity).firstOrNull {
            (it.email eq email)
        }
        return result?.toUserData()
    }

    override suspend fun update(user: UserPayload): Boolean {
        TODO("Not yet implemented")
    }

    private suspend fun DbUserEntity.toUser(): Response {
        val data = toUserData()
        data.token = tokenManager.generateToken(data.userId)

        val result = UserDto(
            status = true,
            data = data
        )

        return result.toUser()
    }
}