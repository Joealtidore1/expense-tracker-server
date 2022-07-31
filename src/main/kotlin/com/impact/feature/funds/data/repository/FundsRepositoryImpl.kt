package com.impact.feature.funds.data.repository

import com.impact.database.DatabaseManager
import com.impact.feature.funds.data.local.FundsEntity
import com.impact.feature.funds.domain.model.FundData
import com.impact.feature.funds.domain.model.payload.FundsPayload
import com.impact.feature.funds.domain.repository.FundsRepository
import org.ktorm.dsl.*
import org.ktorm.entity.first
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf

class FundsRepositoryImpl : FundsRepository {
    private val db = DatabaseManager.dbConnection
    override suspend fun addFunds(fundsPayload: FundsPayload): Int {
        return  db.insertAndGenerateKey(FundsEntity){
            set(FundsEntity.month, fundsPayload.month)
            set(FundsEntity.amount, fundsPayload.amount.toBigDecimal())
            set(FundsEntity.userId, fundsPayload.userId)
            set(FundsEntity.fundingId, fundsPayload.fundingId)
            set(FundsEntity.year, fundsPayload.year.toLong())
        } as Int
    }

    override suspend fun getFunds(userId: String): List<FundData.Data> {
        val funds = mutableListOf<FundData.Data>()

        val funding = db.sequenceOf(FundsEntity).query.where{
            FundsEntity.userId eq userId
        }

        funding.forEach {
            val fund = FundData.Data(
                id = it[FundsEntity.id]!!,
                fundingId = it[FundsEntity.fundingId]!!,
                amount = it[FundsEntity.amount]!!.toDouble(),
                month = it[FundsEntity.month]!!,
                year = it[FundsEntity.year]!!,
                createdAt = it[FundsEntity.createdAt]!!,
                updatedAt = it[FundsEntity.updateAt]
            )

            funds.add(fund)
        }

        return funds
    }

    override suspend fun getFunds(month: String?, year: Long?, userId: String): List<FundData.Data> {
        val funds = mutableListOf<FundData.Data>()

        val funding = db.sequenceOf(FundsEntity).query.where{
            if(month != null && year != null){
                (FundsEntity.month eq month) and (FundsEntity.userId eq userId) and (FundsEntity.year eq year)
            } else if(year != null){
                (FundsEntity.userId eq userId) and (FundsEntity.year eq year)
            }else{
                (FundsEntity.month eq month!!) and (FundsEntity.userId eq userId)
            }
        }
        funding.totalRecords
        funding.forEach {
            val fund = FundData.Data(
                id = it[FundsEntity.id]!!,
                fundingId = it[FundsEntity.fundingId]!!,
                amount = it[FundsEntity.amount]!!.toDouble(),
                month = it[FundsEntity.month]!!,
                year = it[FundsEntity.year]!!,
                createdAt = it[FundsEntity.createdAt]!!,
                updatedAt = it[FundsEntity.updateAt]
            )

            funds.add(fund)
        }

        return funds

    }

    override suspend fun getBalance(userId: String): Double {
        var res = 0.0
       db.sequenceOf(FundsEntity).query.where {
            FundsEntity.userId eq  userId
        }.forEach {
            res += it[FundsEntity.amount]?.toDouble()?:0.0
        }

        return res
    }

    override suspend fun updateFunding(month: String?, amount: Double?, year: Long?, fundingId: Long?, userId: String): Int {
        return db.update(FundsEntity){
            if(!month.isNullOrBlank()){
                set(FundsEntity.month, month)
            }
            if(amount != null){
                set(FundsEntity.amount, amount.toBigDecimal())
            }

            if(year != null){
                set(FundsEntity.year, year)
            }
            where {
                FundsEntity.userId eq userId and (FundsEntity.fundingId eq fundingId!!)
            }
        }
    }

    override suspend fun deleteFunding(fundingId: Long, userId: String): Int {
        return db.delete(FundsEntity){
            FundsEntity.fundingId eq fundingId and (FundsEntity.userId eq userId)
        }
    }

    override suspend fun getFundingDate(id: Int): String{
        return db.sequenceOf(FundsEntity).first {
            it.id eq id
        }.createdAt!!
    }

    override suspend fun verifyFundingId(fundingId: Long?, userId: String): Boolean {
        return fundingId == null || db.sequenceOf(FundsEntity).firstOrNull{
            FundsEntity.fundingId eq fundingId and (FundsEntity.userId eq userId)
        } != null
    }

}