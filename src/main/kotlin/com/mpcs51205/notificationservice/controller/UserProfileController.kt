package com.mpcs51205.notificationservice.controller

import com.mpcs51205.notificationservice.model.UserProfile
import com.mpcs51205.notificationservice.service.UserProfileService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/notification")
class UserProfileController(val userProfileService: UserProfileService) {
    @GetMapping("/user-profile/{userId}")
    fun getUserProfile(@PathVariable userId:UUID): UserProfile = userProfileService.getUserProfileById(userId)

    @DeleteMapping("/user-profile/{userId}")
    fun deleteUserProfile(@PathVariable userId: UUID) = userProfileService.deleteUserProfile(userId)

    @PostMapping("/user-profile")
    fun createUserProfile(@RequestBody userProfile: UserProfile) = userProfileService.createUserProfile(userProfile)


    // Don't expose this endpoint in production, testing only 
    //@PostMapping("/user")
    //fun triggerUserCreationEvent(@RequestBody auctionStart: AuctionStartOrEndSoon) =eventSenderService.createUserEvent(auctionStart)

    // #todo: add update user-profile

    @GetMapping("/user-profile")
    fun getAllUserProfiles() = userProfileService.getUserProfiles()





}