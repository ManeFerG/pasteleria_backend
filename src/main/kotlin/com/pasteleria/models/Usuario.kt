
package com.pasteleria.models

data class Usuario(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val password: String,
    val telefono: String?,
    val direccion: String,
    val edad: Int,
)
