package com.mpcs51205.notificationservice.repository

import com.mpcs51205.notificationservice.model.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmailRepository: JpaRepository<Email, UUID> {
    // #todo: get all emails by sender id
    fun getEmailsBySenderId(senderId: UUID): Array<Email>

    // #todo: get all emails by receiver id
    fun getEmailsByReceiverId(receiverId: UUID): Array<Email>

    // #todo: get all emails by template type
    fun getEmailsByTemplateType(templateType: String): Array<Email>
}