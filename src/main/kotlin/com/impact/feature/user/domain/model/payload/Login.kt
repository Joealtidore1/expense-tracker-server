package com.impact.feature.user.domain.model.payload

import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.validate

@kotlinx.serialization.Serializable
data class Login(
    val email: String? = null,
    val phone: String? = null,
    val password: String
){
    init {
        validate(this){
            if(email.isNullOrBlank()){
                validate(Login::phone).isNotNull().isNotBlank()
            }
            if(phone.isNullOrBlank()){
                validate(Login::email).isNotNull().isNotBlank().isEmail()
            }
            validate(Login::password).isNotNull().isNotBlank()
        }
    }
}
