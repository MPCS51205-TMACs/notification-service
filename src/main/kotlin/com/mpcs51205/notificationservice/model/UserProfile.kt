package com.mpcs51205.notificationservice.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*
import javax.persistence.*


@Entity
@Table(indexes = [Index(columnList = "id")])
class UserProfile: Serializable {
    @Id
    @Column(nullable = false)
    var id: UUID

    @Column(nullable = false)
    lateinit var name: String

    @Column(unique=true, nullable = false)
    lateinit var email: String

    @Column
    var receiveAlerts: Boolean = true

    constructor(id: UUID, name: String, email: String) {
        this.id = id
        this.name = name
        this.email = email
        this.receiveAlerts = true
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserProfileUpdate: Serializable {
    var id: UUID? = null
    var name: String? = null
    var email: String? = null
    var receiveAlerts: Boolean? = null

    fun update(userProfile: User) {
        userProfile.name = this.name ?: userProfile.name
        userProfile.email = this.email ?: userProfile.email

        if (userProfile.suspended.toString() == "true" || userProfile.active.toString() == "false"){
            this.receiveAlerts = false
        }
        if (userProfile.suspended.toString() == "false" && userProfile.active.toString() == "true"){
            this.receiveAlerts = false
        }
        return
    }
}

class UserProfileUpdateEvent(val userId: UUID, val update: UserProfileUpdate): Serializable