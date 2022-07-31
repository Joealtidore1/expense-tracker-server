package com.impact.feature.funds.domain.services

import com.impact.feature.funds.data.repository.FundsRepositoryImpl
import com.impact.feature.funds.domain.repository.FundsRepository
import org.koin.java.KoinJavaComponent.inject

open class FundService {
    val repo: FundsRepository by inject(FundsRepositoryImpl::class.java)
}