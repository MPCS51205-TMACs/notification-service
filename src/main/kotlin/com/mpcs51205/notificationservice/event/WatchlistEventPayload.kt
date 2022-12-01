package com.mpcs51205.notificationservice.event

import java.io.Serializable

class WatchlistMatch: Serializable {
    lateinit var userId: String
    lateinit var itemId: String
}