package com.impact.database

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.ktorm.database.Database

object DatabaseManager {
    private val config = HoconApplicationConfig(ConfigFactory.load())
    val dbConnection: Database
    init {
        val username = config.property("db.username").getString()
        val url = config.property("db.url").getString()
        val password = config.property("db.password").getString()
        println(url)
        dbConnection = Database.connect (
            url = url,
            driver = "com.mysql.cj.jdbc.Driver",
            user = username,
            password = password
        )
    }
}