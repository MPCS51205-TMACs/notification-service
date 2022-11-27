package com.mpcs51205.notificationservice.model

import java.io.Serializable
import java.util.*

class User: Serializable {
    var id: UUID? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var paymentMethod: String? = null
    var admin: Boolean = false
    var suspended: Boolean = false
    var active: Boolean? = true
    fun getRoles(): List<String> = if (admin) listOf("ROLE_USER","ROLE_ADMIN") else listOf("ROLE_USER")
}
