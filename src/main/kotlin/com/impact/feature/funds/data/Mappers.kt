package com.impact.feature.funds.data

import com.impact.feature.funds.data.local.DbFundsEntity
import com.impact.feature.funds.domain.model.FundData

fun DbFundsEntity.toFundingData() = FundData.Data(
    id = id!!,
    fundingId = fundingId,
    amount = amount.toDouble(),
    month = month,
    year = year,
    createdAt = createdAt!!,
    updatedAt = updatedAt
)