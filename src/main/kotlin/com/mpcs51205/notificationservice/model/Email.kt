package com.mpcs51205.notificationservice.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 *
-id: uuid
-senderId: int
-receiverId: int
-senderEmail: String
-receiverEmail: String
-senderName: String
-receiverName: String
-sentDate: DateTime
-subject: String
-body: String
-templateType: Enum
 */

@Entity
@Table(indexes = [Index(columnList = "receiverEmail")])
class Email: Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(nullable = false)
    var id: UUID? = null

    @Column(nullable = false)
    var senderId: UUID? = null

    @Column(nullable = false)
    var receiverId: UUID? = null

    @Column(nullable = false)
    lateinit var senderEmail: String

    @Column(nullable = false)
    lateinit var receiverEmail: String

    @Column(nullable = false)
    lateinit var senderName: String

    @Column(nullable = false)
    lateinit var receiverName: String

    @Column(updatable = false)
    @CreationTimestamp
    lateinit var sentDate: LocalDateTime

    @Column(nullable = false)
    lateinit var subject: String

    @Column(nullable = false)
    lateinit var body: String

    // change to enum?
    @Column(nullable = false)
    lateinit var templateType: String
}