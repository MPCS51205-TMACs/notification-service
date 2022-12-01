package com.mpcs51205.notificationservice.service

import com.mpcs51205.notificationservice.event.*
import com.mpcs51205.notificationservice.exception.*
import com.mpcs51205.notificationservice.model.*
import com.mpcs51205.notificationservice.repository.EmailRepository
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
class EmailService(
    val emailRepository: EmailRepository,
    val userProfileRepository: UserProfileRepository,
    val rabbitConfig: RabbitConfig
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
        emailRepository.save(email)
    }

    fun deleteEmail(emailId: UUID) = emailRepository.delete(getEmailReference(emailId))

    private fun getEmailReference(emailId: UUID): Email = emailRepository.getReferenceById(emailId)
    fun getEmails(): MutableList<Email> = emailRepository.findAll()

    fun sendWatchlistMatchEmail(watchlistMatch: WatchlistMatch) {
        // get the profile of the user id provided
        var userProfile = UserProfile()
        try {
            //userProfile = userProfileRepository.getUserProfileById(watchlistMatch.userId)
            //userProfile.email
        } catch (e: Exception) {
            println("NOT FOUND: Can't find user with id:" + watchlistMatch.userId)
        }

        var email = Email()
        //email.id?.let { emailRepository.getReferenceById(it) }

        email.sentDate = LocalDateTime.now()
        email.senderId = watchlistMatch.userId
        email.receiverId = watchlistMatch.userId
        email.senderName = "Admin"
        email.receiverName = "Your name"
        email.senderEmail = "alerts@webay.com"
        email.receiverEmail = "email"
        email.templateType = "watchlist"
        email.body = "Hello, " + email.receiverName + "recently added Item" + watchlistMatch.item.description + " matches your watchlist!"
        email.subject = "An Item Matches Your Watchlist!"

        //email.id?.let { emailRepository.getReferenceById(it) }
        //emailRepository.save(email)
    }

    fun sendNewHighBidEmail(newHighBid: AuctionNewHighBid) {
        var email = Email()
        //email.id?.let { emailRepository.getReferenceById(it) }
        email.sentDate = LocalDateTime.now()
        email.senderId = UUID.fromString("874555dc-7974-4229-ba03-6b000275fca2")
        email.receiverId = UUID.fromString("874555dc-7974-4229-ba03-6b000275fca2")
        email.senderName = "Admin"
        email.receiverName = "Your name"
        email.senderEmail = "alerts@webay.com"
        email.receiverEmail = "email"
        email.templateType = "new high bid"
        email.body = "New High Bid email"
        email.subject = "New High Bid Email"
    }

    fun sendAuctionStartOrEndSoonEmail(auctionStartOrEndSoon: AuctionStartOrEndSoon) {
        var email = Email()
        //email.id?.let { emailRepository.getReferenceById(it) }
        email.sentDate = LocalDateTime.now()
        email.senderId = UUID.fromString("874555dc-7974-4229-ba03-6b000275fca2")
        email.receiverId = UUID.fromString("874555dc-7974-4229-ba03-6b000275fca2")
        email.senderName = "Admin"
        email.receiverName = "Your name"
        email.senderEmail = "alerts@webay.com"
        email.receiverEmail = "email"
        email.templateType = "auctionAlert"
        email.body = "Auction Alert- start or end soon"
        email.subject = "Auction Alert - start or end soon"
    }

    fun sendAuctionEndEmail(auctionEnd: AuctionEnd): Email {
        println("Sending auction end email now")
        var email = Email()
        //email.id?.let { emailRepository.getReferenceById(it) }
        email.sentDate = LocalDateTime.now()
        email.senderId = UUID.fromString("874555dc-7974-4229-ba03-6b000275fca2")
        email.receiverId = UUID.fromString("874555dc-7974-4229-ba03-6b000275fca2")
        email.senderName = "Admin"
        email.receiverName = "Your name"
        email.senderEmail = "alerts@webay.com"
        email.receiverEmail = "email"
        email.templateType = "watchlist"
        email.body = "Auction end alert"
        email.subject = "Auction end alert"

        email = emailRepository.save(email)
        println(email.body)
        return email
    }
    fun sendWatchlistEvent() {
        var item = Item()
        item.id = UUID.fromString("874555dc-7974-4229-ba03-6b000275fca2")
        item.userId = UUID.fromString("0fa23072-f5fc-485b-b307-6156592a7847")
        item.description = "Rubber Ducky 5"
        item.quantity = 1
        item.price = 100.0
        item.startPrice = 50.0
        item.shippingCosts = 1.0
        item.startTime = Date.from(Instant.now())
        item.endTime = Date.from(Instant.now())
        item.buyNow = true
        item.counterfeit = false
        item.inappropriate = false
        item.categories = mutableListOf<ItemCategory>()
        item.bookmarks = mutableListOf<ItemBookmark>()

        rabbitConfig.sendMatchEvent(UUID.fromString("a15156d4-d359-4f97-80e3-c02d1fdc3d8e"), item)
    }

    fun sendEvents() {
        sendWatchlistEvent()
    }

}