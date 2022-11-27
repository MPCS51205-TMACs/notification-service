package com.mpcs51205.notificationservice.model

import java.io.Serializable

class AuctionBid: Serializable {
    lateinit var bid_id: String
    lateinit var item_id: String
    lateinit var bidder_user_id: String
    lateinit var time_received: String
    var amount_in_cents: Int? = 0
    var active: Boolean? = null
}