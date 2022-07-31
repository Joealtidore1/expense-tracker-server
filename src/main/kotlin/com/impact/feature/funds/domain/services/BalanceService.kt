package com.impact.feature.funds.domain.services

import com.impact.feature.funds.domain.model.Balance
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.EmptyCoroutineContext

object BalanceService: FundService() {

    suspend fun getBalance(userId: String): Resource<Response>{
        val fundsDeferred = CoroutineScope(EmptyCoroutineContext).async {
            repo.getBalance(userId)
        }

        val expenseDeferred = CoroutineScope(EmptyCoroutineContext).async {
            repo.getBalance(userId)
        }

        val result = Balance(
            status = true,
            data = Balance.Data(
                balance = fundsDeferred.await() + expenseDeferred.await()
            )
        )

        return Resource.Success(
            data = result,
            statusCode = HttpStatusCode.OK
        )
    }
}