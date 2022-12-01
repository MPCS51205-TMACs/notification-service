package com.mpcs51205.notificationservice.controller

import com.mpcs51205.notificationservice.model.Email
import com.mpcs51205.notificationservice.service.EmailService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/notification")
class EmailController (val emailService: EmailService) {
    @GetMapping("/email/{emailId}")
    fun getEmail(@PathVariable emailId: UUID): Email = emailService.getEmailById(emailId)

    @DeleteMapping("/email/{emailId}")
    fun deleteEmail(@PathVariable emailId: UUID) = emailService.deleteEmail(emailId)

    @PostMapping("/email")
    fun createEmail(@RequestBody email: Email) = emailService.createEmail(email)

    @GetMapping("/email/inbox/{userId}")
    fun getEmailsByReceiverId(@PathVariable userId: UUID): Array<Email> = emailService.getEmailsByReceiverId(userId)

    @GetMapping("/email/outbox/{userId}")
    fun getEmailsBySenderId(@PathVariable userId: UUID): Array<Email> = emailService.getEmailsBySenderId(userId)

    @GetMapping("/email/template/{templateType}")
    fun getEmailsByTemplate(@PathVariable templateType: String): Array<Email> = emailService.getEmailsByTemplateType(templateType)

    @GetMapping("/email")
    fun getAllUserEmails() = emailService.getEmails()
}