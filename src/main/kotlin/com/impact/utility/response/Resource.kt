package com.impact.utility.response

import io.ktor.http.*

sealed class Resource<T> (val data: T, val statusCode: HttpStatusCode? = null){
    class Success<T>(data: T, statusCode: HttpStatusCode? = null): Resource<T>(data, statusCode)
    class Failure<T>(data:  T, statusCode: HttpStatusCode? = null): Resource<T>(data = data, statusCode)
}
