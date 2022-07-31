package com.impact.feature.funds.data.local

import com.impact.feature.user.data.local.UserEntity.primaryKey
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.math.BigDecimal
import java.math.BigInteger

object FundsEntity: Table<DbFundsEntity>("funds"){
    val id = int("id").primaryKey().bindTo { it.id }
    val month = varchar("month").bindTo { it.month }
    val amount = decimal("amount").bindTo { it.amount }
    val year = long("year").bindTo { it.year }
    val fundingId = long("fundingId").bindTo { it.fundingId }
    val userId = varchar("userId").bindTo { it.userId }
    val createdAt = varchar("createdAt").bindTo { it.createdAt }
    val updateAt = varchar("updatedAt").bindTo { it.updatedAt }
}

interface DbFundsEntity: Entity<DbFundsEntity>{
    companion object: Entity.Factory<DbFundsEntity>()

    val id: Int?
    val month: String
    val amount: BigDecimal
    val year: Long
    val fundingId: Long
    val userId: String
    val createdAt: String?
    val updatedAt: String?
}