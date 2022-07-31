package com.impact.feature.user.domain.model.payload

import org.mindrot.jbcrypt.BCrypt
import org.valiktor.*
import org.valiktor.functions.*
import java.util.UUID

val uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$".toRegex()
@kotlinx.serialization.Serializable
data class UserPayload(
    val name: String = "",
    val phone: String = "",
    val gender: String? = null,
    val email: String? = null,
    val userId: String = UUID.randomUUID().toString(),
    val password: String = "",
    val confirmPassword: String = ""
){
    init {
        validate(this){
            validate(UserPayload::name).isNotNull().isNotBlank()
            validate(UserPayload::phone).isNotNull().isNotBlank()
            validate(UserPayload::password).isNotBlank().isNotBlank()
            validate(UserPayload::password).hasSize(min = 6)
            validate(UserPayload::userId).matches(uuidRegex)
            if(!email.isNullOrBlank()){
                validate(UserPayload::email).isEmail()
            }
        }
    }
    fun hashedPassword(): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}
