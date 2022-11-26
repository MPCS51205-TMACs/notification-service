package com.mpcs51205.notificationservice.model

import java.io.Serializable
import java.util.*

class User: Serializable {
    var id: UUID? = null
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit var paymentMethod: String
    var admin: Boolean = false
    var suspended: Boolean = false
    fun getRoles(): List<String> = if (admin) listOf("ROLE_USER","ROLE_ADMIN") else listOf("ROLE_USER")
}
