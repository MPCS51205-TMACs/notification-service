package com.mpcs51205.notificationservice.event

import com.mpcs51205.notificationservice.model.*
import com.mpcs51205.notificationservice.service.EmailService
import com.mpcs51205.notificationservice.service.UserProfileService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class RabbitSubscriber(val userProfileService: UserProfileService, val emailService: EmailService) {
    @RabbitListener(queues = ["notification-service:user.create"])
    fun receiveUserCreate(user: UserCreate) {
        println("Receiving message create")
        val userProfile = user.id?.let { user.name?.let { it1 -> user.email?.let { it2 -> UserProfile(it, it1, it2) } } }
        if (userProfile != null) {
            userProfileService.createUserProfile(userProfile)
        }
    }

    @RabbitListener(queues = ["notification-service:user.delete"])
    fun receiveUserDelete(userDeleteEvent: UserDelete) {
        println("Receiving message user delete")
        userProfileService.deleteUserProfile(userDeleteEvent.userId)
    }

    @RabbitListener(queues = ["notification-service:user.update"])
    fun receiveUserUpdate(userUpdateEvent: UserUpdateEvent) {
        println("Receiving message User Update")
        userProfileService.updateUserProfile(userUpdateEvent)
    }

    // add other events here
    @RabbitListener(queues = ["notification-service:user.activation"])
    fun receiveUserActivation(userActivation: UserActivation) {
        println("Receiving message user activate")
        userProfileService.updateUserProfileActivation(userActivation.userId, userActivation.active)
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userProfile != null) {
        //    notificationService.createUserProfile(userProfile)
       // }
    }

    @RabbitListener(queues = ["notification-service:watchlist.match"])
    fun receiveWatchlistMatch(watchlistMatch: WatchlistMatch) {
        println("Receiving watchlist Match")
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userPfrofile != null) {
        //    notificationService.createUserProfile(userProfile)
        // }
    }

    @RabbitListener(queues = ["notification-service:auction.start-soon"])
    fun receiveAuctionStartSoon(auctionStartSoon: AuctionStartOrEndSoon) {
        println("Receiving auction start soon")
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userProfile != null) {
        //    notificationService.createUserProfile(userProfile)
        // }
    }

    @RabbitListener(queues = ["notification-service:auction.end-soon"])
    fun receiveAuctionEndSoon(auctionEndSoon: AuctionStartOrEndSoon) {
        println("Receiving auction end soon")
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userProfile != null) {
        //    notificationService.createUserProfile(userProfile)
        // }
    }

    @RabbitListener(queues = ["notification-service:auction.new-high-bid"])
    fun receiveAuctionNewHighBid(auctionNewHighBid: AuctionNewHighBid) {
        println("Receiving auction new high bid")
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userProfile != null) {
        //    notificationService.createUserProfile(userProfile)
        // }
    }

    @RabbitListener(queues = ["notification-service:auction.end"])
    fun receiveAuctionEnd(auction: AuctionEnd) {
        println("Receiving auction end")
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userProfile != null) {
        //    notificationService.createUserProfile(userProfile)
        // }
    }


    @Bean
    open fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

}
