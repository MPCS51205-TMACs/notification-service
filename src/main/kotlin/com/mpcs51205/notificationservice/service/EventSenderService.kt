package com.mpcs51205.notificationservice.service

import com.mpcs51205.notificationservice.event.RabbitPublisher
import com.mpcs51205.notificationservice.event.RabbitSubscriber
import com.mpcs51205.notificationservice.exception.*
import com.mpcs51205.notificationservice.model.Email
import com.mpcs51205.notificationservice.model.User
import com.mpcs51205.notificationservice.model.UserProfile
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import com.mpcs51205.notificationservice.repository.EmailRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

/**
 * for testing purposes only!!
 */
@Service
class EventSenderService(
    val rabbitSubscriber: RabbitSubscriber
) {

    fun createUserEvent(user: User): User {
        //rabbitSubscriber.sendCreateEvent(user)
        rabbitSubscriber.sendUpdateEvent(user)
        rabbitSubscriber.sendDeleteEvent(user)
        rabbitSubscriber.sendActivationEvent(user)
        rabbitSubscriber.sendWatchlistEvent(user)
        rabbitSubscriber.sendAuctionStartSoonEvent(user)
        rabbitSubscriber.sendAuctionEndSoonEvent(user)
        rabbitSubscriber.sendAuctionNewHighBidEvent(user)
        rabbitSubscriber.sendAuctionEndEvent(user)
        return user
    }

}