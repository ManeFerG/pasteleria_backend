
package com.pasteleria.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object Database {
    private val config = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://localhost:3306/pasteleria_db"
        username = "root"
        password = ""
        maximumPoolSize = 5
    }
    private val ds = HikariDataSource(config)

    fun connection(): Connection = ds.connection
}
