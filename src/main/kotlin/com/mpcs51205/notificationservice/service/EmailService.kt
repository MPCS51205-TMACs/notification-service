package com.mpcs51205.notificationservice.service

import com.mpcs51205.notificationservice.event.UserUpdateEvent
import com.mpcs51205.notificationservice.exception.*
import com.mpcs51205.notificationservice.model.*
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import com.mpcs51205.notificationservice.repository.EmailRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmailService(
    val emailRepository: EmailRepository
) {
    fun getEmailById(emailId: UUID): Email = emailRepository.findByIdOrNull(emailId) ?: throw ResourceDoesNotExistException()

    // add get emails by sender
    fun getEmailsByReceiverId(receiverId: UUID): Array<Email> = emailRepository.getEmailsByReceiverId(receiverId)

    // add get email by receiver
    fun getEmailsBySenderId(senderId: UUID): Array<Email> = emailRepository.getEmailsBySenderId(senderId)

    // add get emails by template type
    fun getEmailsByTemplateType(templateType: String): Array<Email> = emailRepository.getEmailsByTemplateType(templateType)

    fun createEmail(email: Email): Email {
        saveEmail(email)
        return email
    }

    fun saveEmail(email: Email) {
        try {
            emailRepository.save(email)
        } catch (e: DataIntegrityViolationException) {
            throw EmailAlreadyExists()
        }
    }

    fun deleteEmail(emailId: UUID) = emailRepository.delete(getEmailReference(emailId))

    private fun getEmailReference(emailId: UUID): Email = emailRepository.getReferenceById(emailId)
    fun getEmails(): MutableList<Email> = emailRepository.findAll()

}