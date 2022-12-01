package com.mpcs51205.notificationservice.event

import com.mpcs51205.notificationservice.model.*
import com.mpcs51205.notificationservice.service.NotificationService
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*


@Component
class RabbitSubscriber(val notificationService: NotificationService) {
    /**
    exchange-user-create: user.create
    exchange-user-delete: user.delete
    exchange-user-update: user.update
    exchange-user-activation: user.activation
    exchange-watchlist-match: watchlist.match
    exchange-auction-startstoon: auction.start-soon
    exchange-auction-endsoon: auction.end-soon
    exchange-auction-end: auction.end
    exchange-auction-new-high-bid: auction.new-high-bid
     **/

    // previously was just autowired, but caused dependency cycle
    @Lazy
    @Autowired
    lateinit var template: RabbitTemplate

    var routingKey = ""

    @Value("\${spring.rabbitmq.template.exchange-user-create}")
    lateinit var userCreateExchange: String

    @Value("\${spring.rabbitmq.template.exchange-user-update}")
    lateinit var userUpdateExchange: String

    @Value("\${spring.rabbitmq.template.exchange-user-delete}")
    lateinit var userDeleteExchange: String

    @Value("\${spring.rabbitmq.template.exchange-user-activation}")
    lateinit var userActivationExchange: String

    @Value("\${spring.rabbitmq.template.exchange-watchlist-match}")
    lateinit var watchlistMatchExchange: String

    @Value("\${spring.rabbitmq.template.exchange-auction-startsoon}")
    lateinit var auctionStartSoonExchange: String

    @Value("\${spring.rabbitmq.template.exchange-auction-endsoon}")
    lateinit var auctionEndSoonExchange: String

    @Value("\${spring.rabbitmq.template.exchange-auction-new-high-bid}")
    lateinit var auctionNewHighBidExchange: String

    @Value("\${spring.rabbitmq.template.exchange-auction-end}")
    lateinit var auctionEndExchange: String


    @Bean
    fun userCreateExchange() = FanoutExchange(userCreateExchange, true, false)
    @Bean
    fun userUpdateExchange() = FanoutExchange(userUpdateExchange, true, false)
    @Bean
    fun userActivationExchange() = FanoutExchange(userActivationExchange, true, false)
    @Bean
    fun userDeleteExchange() = FanoutExchange(userDeleteExchange, true, false)

    // change to fanout exchange for now to integrate w/ watchlist service
    @Bean
    fun watchlistMatchExchange() = FanoutExchange(watchlistMatchExchange, true, false)

    @Bean
    fun auctionStartSoonExchange() = DirectExchange(auctionStartSoonExchange, true, false)

    @Bean
    fun auctionEndSoonExchange() = DirectExchange(auctionEndSoonExchange, true, false)

    @Bean
    fun auctionNewHighBidExchange() = DirectExchange(auctionNewHighBidExchange, true, false)

    @Bean
    fun auctionEndExchange() = FanoutExchange(auctionEndExchange, true, false)

    @Bean
    fun userCreateQueue(): Queue = Queue("notification-service:user.create",true)

    @Bean
    open fun userCreateBinding(): Binding = Binding(userCreateQueue().name, Binding.DestinationType.QUEUE, userCreateExchange().name, routingKey, null)

    @Bean
    fun userDeleteQueue(): Queue = Queue("notification-service:user.delete",true)
    @Bean
    open fun userDeleteBinding(): Binding = Binding(userDeleteQueue().name, Binding.DestinationType.QUEUE, userDeleteExchange().name, routingKey, null)

    @Bean
    fun userUpdateQueue(): Queue = Queue("notification-service:user.update",true)
    @Bean
    open fun userUpdateBinding(): Binding = Binding(userUpdateQueue().name, Binding.DestinationType.QUEUE, userUpdateExchange().name, routingKey, null)

    @Bean
    fun userActivationQueue(): Queue = Queue("notification-service:user.activation",true)
    @Bean
    open fun userActivationBinding(): Binding = Binding(userActivationQueue().name, Binding.DestinationType.QUEUE, userActivationExchange().name, routingKey, null)

    @Bean
    fun watchlistMatchQueue(): Queue = Queue("notification-service:watchlist.match",true)
    @Bean
    open fun watchlistMatchBinding(): Binding = Binding(watchlistMatchQueue().name, Binding.DestinationType.QUEUE, userActivationExchange().name, routingKey, null)

    @Bean
    fun auctionStartSoonQueue(): Queue = Queue("notification-service:auction.start-soon",true)
    @Bean
    open fun auctionStartSoonBinding(): Binding = Binding(auctionStartSoonQueue().name, Binding.DestinationType.QUEUE, auctionStartSoonExchange().name, routingKey, null)

    @Bean
    fun auctionEndSoonQueue(): Queue = Queue("notification-service:auction.end-soon",true)
    @Bean
    open fun auctionEndSoonBinding(): Binding = Binding(auctionEndSoonQueue().name, Binding.DestinationType.QUEUE, auctionEndSoonExchange().name, routingKey, null)

    @Bean
    fun auctionNewHighBidQueue(): Queue = Queue("notification-service:auction.new-high-bid",true)
    @Bean
    open fun auctionNewHighBidBinding(): Binding = Binding(auctionNewHighBidQueue().name, Binding.DestinationType.QUEUE, auctionNewHighBidExchange().name, routingKey, null)

    @Bean
    fun auctionEndQueue(): Queue = Queue("notification-service:auction.end",true)
    @Bean
    open fun auctionEndBinding(): Binding = Binding(auctionEndQueue().name, Binding.DestinationType.QUEUE, auctionEndExchange().name, routingKey, null)

    @RabbitListener(queues = ["notification-service:user.create"])
    fun receiveUserCreate(user: User) {
        println("Receiving message create")
        val userProfile = user.id?.let { user.name?.let { it1 -> user.email?.let { it2 -> UserProfile(it, it1, it2) } } }
        if (userProfile != null) {
            notificationService.createUserProfile(userProfile)
        }
    }

    @RabbitListener(queues = ["notification-service:user.delete"])
    fun receiveUserDelete(userId: UUID) {
        println("Receiving message user delete")
        notificationService.deleteUserProfile(userId)
    }

    @RabbitListener(queues = ["notification-service:user.update"])
    fun receiveUserUpdate(userUpdateEvent: UserUpdateEvent) {
        println("Receiving message User Update")
        notificationService.updateUserProfile(userUpdateEvent)
    }

    // add other events here
    @RabbitListener(queues = ["notification-service:user.activation"])
    fun receiveUserActivation(userId: UUID, active: Boolean) {
        println("Receiving message user activate")
        notificationService.updateUserProfileActivation(userId, active)
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


    // FOR TESTING
    fun sendCreateEvent(user: User) = send(exchange = userCreateExchange, payload = user)
    fun sendUpdateEvent(user: User) = send(exchange = userUpdateExchange, payload = user)
    fun sendDeleteEvent(user: User) = send(exchange = userDeleteExchange, payload = user)
    fun sendActivationEvent(user: User) = send(exchange = userActivationExchange, payload = user)
    fun sendWatchlistEvent(watchlistMatch: WatchlistMatch) = send(exchange = watchlistMatchExchange, payload = watchlistMatch)
    fun sendAuctionStartSoonEvent(auctionStart: AuctionStartOrEndSoon) = send(exchange = auctionStartSoonExchange, payload = auctionStart)
    fun sendAuctionEndSoonEvent(auctionEnd: AuctionStartOrEndSoon) = send(exchange = auctionEndSoonExchange, payload = auctionEnd)
    fun sendAuctionNewHighBidEvent(newHighBid: AuctionNewHighBid) = send(exchange = auctionNewHighBidExchange, payload = newHighBid)
    fun sendAuctionEndEvent(auctionEnd: AuctionEnd) = send(exchange = auctionEndExchange, payload = auctionEnd)

    private fun send(exchange: String, payload: Serializable) {
        template.convertAndSend(exchange, routingKey, payload)
    }

    @Bean
    open fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

}
