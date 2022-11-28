package com.mpcs51205.notificationservice.service

import com.mpcs51205.notificationservice.event.RabbitPublisher
import com.mpcs51205.notificationservice.event.RabbitSubscriber
import com.mpcs51205.notificationservice.exception.*
import com.mpcs51205.notificationservice.model.*
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import com.mpcs51205.notificationservice.repository.EmailRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationService(
    val userProfileRepository: UserProfileRepository,
    val emailRepository: EmailRepository
) {
    fun getUserProfileById(userId: UUID): UserProfile = userProfileRepository.findByIdOrNull(userId) ?: throw ResourceDoesNotExistException()

    fun createUserProfile(userProfile: UserProfile): UserProfile {
        saveUserProfile(userProfile)
        return userProfile
    }

    fun saveUserProfile(userProfile: UserProfile) {
        try {
            userProfileRepository.save(userProfile)
        } catch (e: DataIntegrityViolationException) {
            throw EmailAlreadyExists()
        }
    }

    // update userProfile
    fun updateUserProfile(userUpdateEvent: UserUpdateEvent): UserProfile {
        println("updating user Profile")
        val target: UserProfile = getUserProfileReference(userUpdateEvent.userId)
        if (userUpdateEvent.update.email != null) {
            println("Updating email to... " + userUpdateEvent.update.email!!)
            target.email = userUpdateEvent.update.email!!
        }
        if (userUpdateEvent.update.name != null) {
            println("Updating name to... " + userUpdateEvent.update.name!!)
            target.name = userUpdateEvent.update.name!!
        }
        userProfileRepository.save(target)
        return target
    }

    fun deleteUserProfile(userId: UUID) = userProfileRepository.delete(getUserProfileReference(userId))

    fun getUserProfiles(): MutableList<UserProfile> = userProfileRepository.findAll()

    private fun getUserProfileReference(userId: UUID): UserProfile = userProfileRepository.getReferenceById(userId)

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