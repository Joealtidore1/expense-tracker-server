package com.impact.feature.user.domain.services

import com.impact.feature.user.data.repository.UserRepositoryImpl
import com.impact.feature.user.domain.repository.UserRepository
import org.koin.java.KoinJavaComponent.inject

open class UserService {
    val repo: UserRepository by inject(UserRepositoryImpl::class.java)
}