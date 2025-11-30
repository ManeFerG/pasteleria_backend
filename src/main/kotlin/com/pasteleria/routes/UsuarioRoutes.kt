package com.pasteleria.routes

import com.pasteleria.dao.UsuarioDAO
import com.pasteleria.models.Usuario
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.usuarioRoutes() {

    post("/usuarios/registrar") {
        val usuario = call.receive<Usuario>()

        // 1. Validar si el correo ya existe antes de registrar
        if (UsuarioDAO.existeCorreo(usuario.correo)) {
            call.respond(HttpStatusCode.Conflict, mapOf("status" to "error", "message" to "El correo ya está registrado"))
            return@post
        }

        // 2. Si no existe, procedemos a registrar
        val ok = UsuarioDAO.registrar(usuario)
        if (ok) {
            call.respond(HttpStatusCode.Created, mapOf("status" to "ok"))
        } else {
            call.respond(HttpStatusCode.InternalServerError, mapOf("status" to "error", "message" to "Error interno al registrar"))
        }
    }

    post("/usuarios/login") {
        val body = call.receive<Map<String, String>>()
        val correo = body["correo"] ?: ""
        val password = body["password"] ?: ""

        // 1. Validar si el correo existe
        if (!UsuarioDAO.existeCorreo(correo)) {
            call.respond(HttpStatusCode.NotFound, mapOf("status" to "error", "message" to "El correo no está registrado"))
            return@post
        }

        // 2. Intentar login (verificar contraseña)
        val usuario = UsuarioDAO.login(correo, password)
        
        if (usuario != null) {
            call.respond(HttpStatusCode.OK, usuario)
        } else {
            call.respond(HttpStatusCode.Unauthorized, mapOf("status" to "error", "message" to "Contraseña incorrecta"))
        }
    }
}
