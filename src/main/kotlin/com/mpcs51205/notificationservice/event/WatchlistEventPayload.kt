package com.mpcs51205.notificationservice.event

import java.io.Serializable
import java.util.*

class WatchlistMatch: Serializable {
    lateinit var userId: UUID
    lateinit var item: Item
}