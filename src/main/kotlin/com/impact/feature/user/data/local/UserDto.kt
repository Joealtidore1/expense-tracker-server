package com.impact.feature.user.data.local

import com.impact.utility.response.Response

data class UserDto(
    val status: Boolean,
    val data: Data
){
    data class Data(
        val id: Int,
        val name: String,
        val phone: String,
        val gender: String?,
        val email: String?,
        val userId: String,
        val createdAt: String?,
        val updatedAt: String?,
        val password: String,
        var token: String
    ): Response()
}
