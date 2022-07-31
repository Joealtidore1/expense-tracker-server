package com.impact.feature.user.domain.model

import com.impact.utility.response.Response

@kotlinx.serialization.Serializable
data class User(
    val status: Boolean,
    val data: Data
): Response(){
    @kotlinx.serialization.Serializable
    data class Data(
        val id: Int,
        val name: String,
        val phone: String,
        val gender: String?,
        val email: String?,
        val userId: String,
        val createdAt: String?,
        val updatedAt: String?,
        var token: String = ""
    ): Response()
}
