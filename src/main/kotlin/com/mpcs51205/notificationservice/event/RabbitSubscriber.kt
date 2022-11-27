package com.mpcs51205.notificationservice.event

import com.mpcs51205.notificationservice.model.User
import com.mpcs51205.notificationservice.model.UserProfile
import com.mpcs51205.notificationservice.service.NotificationService
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import com.mpcs51205.notificationservice.event.RabbitPublisher
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.io.Serializable


@Component
class RabbitSubscriber(val notificationService: NotificationService) {
    // create user.create events for testing
    /**
    exchange-user-create: user.create
    exchange-user-delete: user.delete
    exchange-user-update: user.update
    exchange-user-activation: user.activation
    exchange-watchlist-match: watchlist.match
    exchange-auction-startstoon: auction.startsoon
    exchange-auction-endsoon: auction.endsoon
    exchange-auction-end: auction.end
    exchange-auction-new-high-bid: auction.new-high-bid
     **/

    @Autowired
    lateinit var template: RabbitTemplate

    @Value("\${spring.rabbitmq.template.exchange-user-create}")
    lateinit var userCreateExchange: String

    @Value("\${spring.rabbitmq.template.exchange-user-update}")
    lateinit var userUpdateExchange: String

    @Value("\${spring.rabbitmq.template.exchange-user-delete}")
    lateinit var userDeleteExchange: String

    @Value("\${spring.rabbitmq.template.exchange-user-activation}")
    lateinit var userActivationExchange: String

    var routingKey = "notification-service"

    //@Value("\${spring.rabbitmq.template.exchange-watchlist-match}")
    //lateinit var watchlistMatchExchange: String

    //@Value("\${spring.rabbitmq.template.exchange-auction-startsoon}")
    //lateinit var auctionStartSoonExchange: String

    //@Value("\${spring.rabbitmq.template.exchange-auction-endsoon}")
    //lateinit var auctionEndSoonExchange: String

    //@Value("\${spring.rabbitmq.template.exchange-auction-new-high-bid}")
    //lateinit var auctionNewHighBidExchange: String

    //@Value("\${spring.rabbitmq.template.exchange-auction-end}")
    //lateinit var auctionEndExchange: String


    @Bean
    fun userCreateExchange() = FanoutExchange(userCreateExchange, true, false)
    @Bean
    fun userUpdateExchange() = FanoutExchange(userUpdateExchange, true, false)
    @Bean
    fun userActivationExchange() = FanoutExchange(userActivationExchange, true, false)
    @Bean
    fun userDeleteExchange() = FanoutExchange(userDeleteExchange, true, false)

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

    // listen to user.create events
    @RabbitListener(queues = ["notification-service:user.create"])
    fun receiveUserCreate(user: User) {
        println("Receiving message create")
        val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        if (userProfile != null) {
            // add exception handling here
            notificationService.createUserProfile(userProfile))
        }
    }

    @RabbitListener(queues = ["notification-service:user.delete"])
    fun receiveUserDelete(user: User) {
        println("Receiving message user delete")
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userProfile != null) {
        //    notificationService.createUserProfile(userProfile)
        //}
    }

    @RabbitListener(queues = ["notification-service:user.update"])
    fun receiveUserUpdate(user: User) {
        println("Receiving message user update")
        //val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        //if (userProfile != null) {
        //    notificationService.createUserProfile(userProfile)
        //}
    }

    // add other events here
    @RabbitListener(queues = ["notification-service:user.activation"])
    fun receiveUserActivation(user: User) {
        println("Receiving message user activate")
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


    private fun send(exchange: String, payload: Serializable) {
        template.convertAndSend(exchange, "notification-service:user.create", payload)
    }


}
