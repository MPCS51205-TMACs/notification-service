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
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


@Component
class RabbitSubscriber(val notificationService: NotificationService) {
    // listen to user.create events
    @RabbitListener(queues = ["notification-service:user.create"])
    fun receive(user: User) {
        println("Receiving message")
        val userProfile = user.id?.let { UserProfile(it, user.name, user.email) }
        if (userProfile != null) {
            notificationService.createUserProfile(userProfile)
        }
    }
}
