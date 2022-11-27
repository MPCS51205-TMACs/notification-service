package com.mpcs51205.notificationservice.model

class AuctionItem {
    lateinit var item_id: String
    lateinit var seller_user_id: String
    lateinit var start_time: String
    lateinit var end_time: String
    var start_price_in_cents: Int? = 0
}