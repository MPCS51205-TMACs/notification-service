package com.mpcs51205.notificationservice.repository

import com.mpcs51205.notificationservice.model.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmailRepository: JpaRepository<Email, UUID> {

}