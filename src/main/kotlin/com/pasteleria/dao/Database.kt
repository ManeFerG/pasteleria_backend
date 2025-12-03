package com.pasteleria.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object Database {

    // Configuración de Hikari usando variables de entorno de Railway
    private val config = HikariConfig().apply {
        // 🔹 Leo variables de Railway (si no existen, uso valores locales por defecto)
        val host = System.getenv("MYSQLHOST") ?: "localhost"
        val port = System.getenv("MYSQLPORT") ?: "3306"
        val db   = System.getenv("MYSQLDATABASE") ?: "pasteleria_db"
        val user = System.getenv("MYSQLUSER") ?: "root"
        val pass = System.getenv("MYSQLPASSWORD") ?: ""

        jdbcUrl = "jdbc:mysql://$host:$port/$db?useSSL=false&serverTimezone=UTC"
        username = user
        password = pass
        maximumPoolSize = 5
    }

    private val ds = HikariDataSource(config)

    fun connection(): Connection = ds.connection
}
