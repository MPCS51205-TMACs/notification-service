package com.mpcs51205.notificationservice.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * -userId: int
-name: String
-email: String
-receiveAlerts: Boolean
 */

@Entity
@Table(indexes = [Index(columnList = "id")])
class UserProfile: Serializable {
    @Id
    @Column(nullable = false)
    var id: UUID? = null

    @Column(nullable = false)
    lateinit var name: String

    @Column(unique=true, nullable = false)
    lateinit var email: String

    @Column
    var receiveAlerts: Boolean = true
}