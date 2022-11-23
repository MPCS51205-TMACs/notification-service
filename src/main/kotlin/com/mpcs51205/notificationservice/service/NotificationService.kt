package com.mpcs51205.notificationservice.service

import com.mpcs51205.notificationservice.exception.*
import com.mpcs51205.notificationservice.model.Email
import com.mpcs51205.notificationservice.model.UserProfile
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

    fun deleteUserProfile(userId: UUID) = userProfileRepository.delete(getUserProfileReference(userId))

    private fun getUserProfileReference(userId: UUID): UserProfile = userProfileRepository.getReferenceById(userId)


    fun getEmailById(emailId: UUID): Email = emailRepository.findByIdOrNull(emailId) ?: throw ResourceDoesNotExistException()

    // add get emails by sender
    // add get email by template type
    // add get emails by receiver

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
}