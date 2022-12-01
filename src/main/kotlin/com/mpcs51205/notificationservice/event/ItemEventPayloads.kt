package com.mpcs51205.notificationservice.event

import java.io.Serializable
import java.util.*

class ItemEventPayloads {
}

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

class ItemBookmark {
    var bookmarkId: UUID? = null
    var userBookmark: UUID? = null
    lateinit var bookmarkedItem: Item
}

class ItemCategory(categoryName: String?): Serializable {
    var id: UUID? = null
    var categoryDescription: String? = categoryName
    var items = mutableListOf<Item>()
}