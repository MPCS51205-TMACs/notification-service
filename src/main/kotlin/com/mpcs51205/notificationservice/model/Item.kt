package com.mpcs51205.notificationservice.model

import java.io.Serializable
import java.util.*


class Item: Serializable {
    var id: UUID? = null
    var userEmail: String? = null
    lateinit var description: String
    var quantity: Int = 0
    var price: Double = 0.0
    var shippingCosts: Double = 0.0
    lateinit var startTime: Date
    lateinit var endTime: Date
    var buyNow: Boolean = true
    var upForAuction: Boolean = true
    var counterfeit: Boolean = false
    var inappropriate: Boolean = false
    var categories = mutableListOf<ItemCategory>()
    var bookmarks: List<ItemBookmark> = mutableListOf()
}