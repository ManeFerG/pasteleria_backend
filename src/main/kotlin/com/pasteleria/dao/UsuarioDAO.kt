package com.pasteleria.dao

import com.pasteleria.models.Usuario

object UsuarioDAO {

    fun existeCorreo(correo: String): Boolean {
        val query = "SELECT 1 FROM usuarios WHERE correo = ? LIMIT 1"
        return Database.connection().use { conn ->
            val stmt = conn.prepareStatement(query)
            stmt.setString(1, correo)
            val rs = stmt.executeQuery()
            rs.next()
        }
    }

    fun registrar(usuario: Usuario): Boolean {
        val query = "INSERT INTO usuarios(nombre, correo, password, telefono, direccion, edad) VALUES (?, ?, ?, ?, ?, ?)"
        return Database.connection().use { conn ->
            val stmt = conn.prepareStatement(query)
            stmt.setString(1, usuario.nombre)
            stmt.setString(2, usuario.correo)
            stmt.setString(3, usuario.password)
            stmt.setString(4, usuario.telefono)
            stmt.setString(5, usuario.direccion)
            stmt.setInt(6, usuario.edad)
            stmt.executeUpdate() > 0
        }
    }

    fun login(correo: String, password: String): Usuario? {
        val query = "SELECT * FROM usuarios WHERE correo = ? AND password = ? LIMIT 1"
        return Database.connection().use { conn ->
            val stmt = conn.prepareStatement(query)
            stmt.setString(1, correo)
            stmt.setString(2, password)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                Usuario(
                    id = rs.getInt("id"),
                    nombre = rs.getString("nombre"),
                    correo = rs.getString("correo"),
                    password = rs.getString("password"),
                    telefono = rs.getString("telefono"),
                    direccion = rs.getString("direccion"),
                    edad = rs.getInt("edad")
                )
            } else null
        }
    }
}
