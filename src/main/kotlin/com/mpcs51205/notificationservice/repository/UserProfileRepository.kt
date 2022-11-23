package com.mpcs51205.notificationservice.repository

import com.mpcs51205.notificationservice.model.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserProfileRepository: JpaRepository<UserProfile, UUID> {
    fun getUserByEmail(email:String): UserProfile?
}