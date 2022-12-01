package com.mpcs51205.notificationservice.service

import com.mpcs51205.notificationservice.event.UserUpdateEvent
import com.mpcs51205.notificationservice.exception.EmailAlreadyExists
import com.mpcs51205.notificationservice.exception.ResourceDoesNotExistException
import com.mpcs51205.notificationservice.model.UserProfile
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserProfileService (val userProfileRepository: UserProfileRepository) {

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

    fun updateUserProfileActivation(userId: UUID, active: Boolean): UserProfile {
        println("updating user Profile activation status for user " + userId)
        val target: UserProfile = getUserProfileReference(userId)
        if (active) {
            println("Activating user" + userId)
            target.receiveAlerts = true
        } else {
            target.receiveAlerts = false
        }
        userProfileRepository.save(target)
        return target
    }


    fun deleteUserProfile(userId: UUID) = userProfileRepository.delete(getUserProfileReference(userId))

    fun getUserProfiles(): MutableList<UserProfile> = userProfileRepository.findAll()

    private fun getUserProfileReference(userId: UUID): UserProfile = userProfileRepository.getReferenceById(userId)

}