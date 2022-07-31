package com.impact.feature.expense.data.repository

import com.impact.database.DatabaseManager
import com.impact.feature.expense.data.local.DbExpenseEntity
import com.impact.feature.expense.domain.model.payload.ExpensePayload
import com.impact.feature.expense.domain.model.payload.ExpenseUpdatePayload
import com.impact.feature.expense.domain.repository.ExpenseRepository
import org.ktorm.dsl.*
import org.ktorm.entity.first
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import com.impact.feature.expense.data.local.ExpenseEntity as e

class ExpenseRepositoryImpl: ExpenseRepository {
    private val db = DatabaseManager.dbConnection
    override suspend fun addExpense(payload: ExpensePayload, userId: String): Int {

        val res = db.insertAndGenerateKey(e){
            set(e.expenseId, payload.expenseId)
            set(e.item, payload.item)
            set(e.type, payload.type)
            set(e.categoryId, payload.categoryId)
            set(e.description, payload.description)
            set(e.amount, payload.amount.toBigDecimal())
            set(e.userId, userId)
            set(e.date, payload.date)
        } as Int

        return res
    }

    override suspend fun getExpense(userId: String): Query {
        return db.sequenceOf(e).query.where {
            e.userId eq userId
        }

    }

    override suspend fun getExpense(userId: String, offset: Int): Query {

        return db.sequenceOf(e).query.where {
            e.userId eq userId
        }.limit(20).offset(offset)
    }

    override suspend fun getExpense(id: Int): DbExpenseEntity {
        return db.sequenceOf(e).first{
            e.id eq id
        }
    }

    override suspend fun getExpense(userId: String, query: String): Query {
        return db.sequenceOf(e).query.where {
            (e.userId eq userId) and
                (
                    (e.type like "%$query%")
                        or (e.description like "%$query%")
                        or (e.item like "%$query%")
                )
        }
    }

    override suspend fun updateExpense(
        payload: ExpenseUpdatePayload,
        expenseId: Long,
        userId: String
    ): Int {
        return db.update(e){
            payload.apply {
                if(!item.isNullOrBlank()){
                    set(e.item, item)
                }
                if(!description.isNullOrBlank()){
                    set(e.description, description)
                }
                if(!type.isNullOrBlank()){
                    set(e.type, type)
                }
                if(categoryId != null){
                    set(e.categoryId, categoryId)
                }
                if(amount != null){
                    set(e.amount, amount.toBigDecimal())
                }
                where {
                    e.userId eq userId and (e.expenseId eq expenseId)
                }
            }
        }
    }

    override suspend fun deleteExpense(userId: String, expenseId: Long): Int {
        return db.delete(e){
            it.expenseId eq expenseId and (it.userId eq userId)
        }


    }

    override suspend fun verifyExpenseId(expenseId: Long): Boolean {
        return db.sequenceOf(e).firstOrNull{
            e.expenseId eq expenseId
        } != null
    }
}