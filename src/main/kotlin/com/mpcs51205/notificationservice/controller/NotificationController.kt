package com.mpcs51205.notificationservice.controller

import com.mpcs51205.notificationservice.model.Email
import com.mpcs51205.notificationservice.model.UserProfile
import com.mpcs51205.notificationservice.service.NotificationService
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import com.mpcs51205.notificationservice.repository.EmailRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/notification")
class NotificationController(val notificationService: NotificationService) {
    // TODO: endpoint permissions
    @GetMapping("/user-profile/{userId}")
    fun getUserProfile(@PathVariable userId:UUID): UserProfile = notificationService.getUserProfileById(userId)

    @DeleteMapping("/user-profile/{userId}")
    fun deleteUserProfile(@PathVariable userId: UUID) = notificationService.deleteUserProfile(userId)

    @PostMapping("/user-profile")
    fun createUserProfile(@RequestBody userProfile: UserProfile) = notificationService.createUserProfile(userProfile)

    // #todo: add update user-profile

    @GetMapping("/email/{emailId}")
    fun getEmail(@PathVariable emailId:UUID): Email = notificationService.getEmailById(emailId)

    @DeleteMapping("/email/{emailId}")
    fun deleteEmail(@PathVariable emailId: UUID) = notificationService.deleteEmail(emailId)

    @PostMapping("/email")
    fun createEmail(@RequestBody email: Email) = notificationService.createEmail(email)

    // level 2: #todo: add additional queries for emails by sender, and receiver emails/ids

}