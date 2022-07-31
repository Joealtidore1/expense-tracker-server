package com.impact.feature.user.data

import com.impact.feature.user.data.local.DbUserEntity
import com.impact.feature.user.data.local.UserDto
import com.impact.feature.user.domain.model.User

fun DbUserEntity.toUserData() = UserDto.Data(
    id = id!!,
    name = name,
    phone = phone,
    gender = gender,
    email = email,
    userId = userId,
    createdAt = createdAt,
    updatedAt = updateAt,
    password = password,
    token = ""
)

fun UserDto.Data.toData() = User.Data(
    id = id,
    name = name,
    phone = phone,
    gender = gender,
    email = email,
    userId = userId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    token = token
)
fun UserDto.toUser() = User(
    status = status,
    data = data.toData()
)

