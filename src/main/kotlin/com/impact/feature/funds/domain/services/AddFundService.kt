package com.impact.feature.funds.domain.services

import com.impact.feature.funds.domain.model.Funds
import com.impact.feature.funds.domain.model.payload.FundsPayload
import com.impact.utility.response.Resource
import com.impact.utility.response.Response
import com.impact.utility.response.ResponseFailure

 object AddFundService: FundService() {
   suspend fun addFunds(fundsPayload: FundsPayload): Resource<Response>{
       val result = repo.addFunds(fundsPayload)

       return  if(result > 0){
           Resource.Success(
               Funds(
                   status = true,
                   data = Funds.Data(
                       balance = repo.getBalance(fundsPayload.userId!!),
                       lastDeposit = fundsPayload.amount,
                       lastDepositDate = repo.getFundingDate(result)
                   )
               )
           )
       }else{
           Resource.Failure(
               ResponseFailure(
                   status = false,
                   message = "Could not add funds"
               )
           )
       }
   }
}