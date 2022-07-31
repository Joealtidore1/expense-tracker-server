package com.impact.feature.expense.domain.services

import com.impact.feature.expense.data.repository.ExpenseRepositoryImpl
import com.impact.feature.expense.domain.repository.ExpenseRepository
import com.impact.utility.response.Response
import org.koin.java.KoinJavaComponent.inject

open class ExpenseServices {
    val repo: ExpenseRepository by inject(ExpenseRepositoryImpl::class.java)
}