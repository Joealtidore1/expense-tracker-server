package com.impact.utility.response

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val status: Boolean,
    val message: String?
)