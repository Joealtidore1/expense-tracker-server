package com.impact.feature.expense.data.local

import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.math.BigDecimal

object ExpenseEntity: Table<DbExpenseEntity>("expense"){
    val id = int("id").bindTo { it.id }.primaryKey()
    val expenseId = long("expenseId").bindTo { it.expenseId }
    val item = varchar("item").bindTo { it.item }
    val description = varchar("description").bindTo { it.description }
    val categoryId = int("categoryId").bindTo { it.categoryId }
    val type = varchar("type").bindTo { it.type }
    val amount = decimal("amount").bindTo { it.amount }
    val userId = varchar("userId").bindTo { it.userId }
    val date = varchar("date").bindTo { it.date }
    val createdAt = varchar("createdAt").bindTo { it.createdAt }
    val updatedAt = varchar("updatedAt").bindTo { it.updatedAt }
}

interface DbExpenseEntity: Entity<DbExpenseEntity>{
    companion object: Entity.Factory<DbExpenseEntity>()

    val id: Int?
    val expenseId: Long
    val item: String
    val description: String?
    val type: String
    val categoryId: Int
    val amount: BigDecimal
    val userId: String
    val date: String
    val createdAt: String?
    val updatedAt: String?
    
}