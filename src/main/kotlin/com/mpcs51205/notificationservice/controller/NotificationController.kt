package com.mpcs51205.notificationservice.controller

import com.mpcs51205.notificationservice.model.AuctionStartOrEndSoon
import com.mpcs51205.notificationservice.model.Email
import com.mpcs51205.notificationservice.model.User
import com.mpcs51205.notificationservice.model.UserProfile
import com.mpcs51205.notificationservice.model.UserProfileUpdate
import com.mpcs51205.notificationservice.service.NotificationService
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import com.mpcs51205.notificationservice.repository.EmailRepository
import com.mpcs51205.notificationservice.service.EventSenderService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(val notificationService: NotificationService, val eventSenderService: EventSenderService) {
    // TODO: endpoint permissions
    @GetMapping("/user-profile/{userId}")
    fun getUserProfile(@PathVariable userId:UUID): UserProfile = notificationService.getUserProfileById(userId)

    @DeleteMapping("/user-profile/{userId}")
    fun deleteUserProfile(@PathVariable userId: UUID) = notificationService.deleteUserProfile(userId)

    @PostMapping("/user-profile")
    fun createUserProfile(@RequestBody userProfile: UserProfile) = notificationService.createUserProfile(userProfile)


    // Don't expose this endpoint in production, testing only 
    //@PostMapping("/user")
    //fun triggerUserCreationEvent(@RequestBody auctionStart: AuctionStartOrEndSoon) =eventSenderService.createUserEvent(auctionStart)

    // #todo: add update user-profile

    // # add get all user-profiles
    @GetMapping("/user-profile")
    fun getAllUserProfiles() = notificationService.getUserProfiles()


    // #get all emails
    @GetMapping("/email")
    fun getAllUserEmails() = notificationService.getEmails()

    @GetMapping("/email/{emailId}")
    fun getEmail(@PathVariable emailId:UUID): Email = notificationService.getEmailById(emailId)

    @DeleteMapping("/email/{emailId}")
    fun deleteEmail(@PathVariable emailId: UUID) = notificationService.deleteEmail(emailId)

    @PostMapping("/email")
    fun createEmail(@RequestBody email: Email) = notificationService.createEmail(email)

    @GetMapping("/email/inbox/{userId}")
    fun getEmailsByReceiverId(@PathVariable userId:UUID): Array<Email> = notificationService.getEmailsByReceiverId(userId)

    @GetMapping("/email/outbox/{userId}")
    fun getEmailsBySenderId(@PathVariable userId:UUID): Array<Email> = notificationService.getEmailsBySenderId(userId)

    @GetMapping("/email/template/{templateType}")
    fun getEmailsByTemplate(@PathVariable templateType: String): Array<Email> = notificationService.getEmailsByTemplateType(templateType)

}