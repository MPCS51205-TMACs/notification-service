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
    var suspended: Boolean? = null
    var active: Boolean? = null
    //fun getRoles(): List<String> = if (admin) listOf("ROLE_USER","ROLE_ADMIN") else listOf("ROLE_USER")
}

class UserUpdate: Serializable {
    var name: String? = null
    var email: String? = null
    var isAdmin: Boolean? = null
    var paymentMethod: String? = null
}
class UserUpdateEvent: Serializable {
    lateinit var userId: UUID
    lateinit var update: UserUpdate
}
