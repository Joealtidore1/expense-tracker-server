package com.impact.feature.user.data.local

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserEntity: Table<DbUserEntity>("user") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name") .bindTo { it.name }
    val phone = varchar("phone") .bindTo { it.phone }
    val email = varchar("email") .bindTo { it.email }
    val gender = varchar("gender") .bindTo { it.gender }
    val userId = varchar("userId") .bindTo { it.userId }
    val password = varchar("password") .bindTo { it.password }
    val createdAt = varchar("createdAt") .bindTo { it.createdAt }
    val updateAt = varchar("updatedAt") .bindTo { it.updateAt }
}

interface DbUserEntity: Entity<DbUserEntity>{
    companion object: Entity.Factory<DbUserEntity>()

    val id: Int?
    val name: String
    val phone: String
    val email: String?
    val gender: String?
    val userId: String
    val password: String
    val createdAt: String?
    val updateAt: String?
}