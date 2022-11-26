package com.mpcs51205.notificationservice.event

import com.mpcs51205.notificationservice.model.User
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.io.Serializable


@Component
@Configuration
class RabbitPublisher {
    // create user.create events for testing
    @Autowired
    lateinit var template: RabbitTemplate

    @Value("\${spring.rabbitmq.template.exchange-create}")
    lateinit var createExchange: String

    @Bean
    fun userCreateExchange() = FanoutExchange(createExchange, true, false)

    @Bean
    fun userCreateQueue(): Queue = Queue("notification-service:user.create",true)

    @Bean
    open fun userCreateBinding( fanout: FanoutExchange?, autoDeleteQueue1: Queue?): Binding? {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanout)
    }

    fun sendCreateEvent(user: User) = send(exchange = createExchange, payload = user)

    private fun send(exchange: String, payload: Serializable) {
        template.convertAndSend(exchange, "notification-service:user.create", payload)
    }

}