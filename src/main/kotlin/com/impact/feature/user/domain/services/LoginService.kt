package com.impact.feature.user.domain.services

import com.impact.feature.user.data.local.UserDto
import com.impact.feature.user.data.toData
import com.impact.feature.user.domain.model.User
import com.impact.feature.user.domain.model.payload.Login
import com.impact.utility.TokenManagerService
import com.impact.utility.TokenManagerServiceImpl
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure
import io.ktor.http.*
import org.koin.java.KoinJavaComponent.inject
import org.mindrot.jbcrypt.BCrypt

object LoginService: UserService() {
    val token: TokenManagerService by inject(TokenManagerServiceImpl::class.java)
    suspend fun login(credentials: Login): Resource<Response>{
        val result = if(credentials.email.isNullOrBlank()){
            loginWithPhone(credentials.phone!!, credentials.password)
        }else{
            loginWithEmail(credentials.email, credentials.password)
        }

        return if(result is ResponseFailure){
            Resource.Failure(
                data = result,
                statusCode = HttpStatusCode.BadRequest
            )
        }else{
            Resource.Success(
                data = User(
                    status = true,
                    data = result as User.Data
                ),
                statusCode = HttpStatusCode.OK
            )
        }
    }

    private suspend fun loginWithEmail(email: String, password: String): Response{
        repo.loginWithEmail(email)?.let {
            val d = it as UserDto.Data
            if(BCrypt.checkpw(password, (d.password))){
                d.token = token.generateToken(d.userId)
                return d.toData()
            }
        }
        return ResponseFailure(
            status = false,
            message = "Email address or password is incorrect"
        )
    }

    private suspend fun loginWithPhone(phone: String, password: String): Response{
        repo.loginWithPhone(phone)?.let {
            val d = it as UserDto.Data
            if(BCrypt.checkpw(password, (d.password))){
                d.token = token.generateToken(d.userId)
                return d.toData()
            }
        }
        return ResponseFailure(
            status = false,
            message = "Phone number or password is incorrect"
        )
    }
}