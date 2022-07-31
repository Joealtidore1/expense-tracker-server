package com.impact.utility.response

import com.impact.utility.response.Response

@kotlinx.serialization.Serializable
data class ResponseFailure(
    val status: Boolean = false,
    val message: String = ""
): Response()