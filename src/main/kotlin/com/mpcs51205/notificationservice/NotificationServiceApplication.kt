package com.mpcs51205.notificationservice

import com.example.notification.event.RabbitSubscriber
import com.mpcs51205.notificationservice.model.UserProfile
import com.mpcs51205.notificationservice.repository.EmailRepository
import com.mpcs51205.notificationservice.repository.UserProfileRepository
import com.mpcs51205.notificationservice.service.NotificationService
import org.springframework.amqp.core.Binding
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class UserServiceApplication

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}