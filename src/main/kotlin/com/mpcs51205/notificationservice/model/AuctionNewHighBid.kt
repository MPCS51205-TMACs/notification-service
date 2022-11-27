package com.mpcs51205.notificationservice.model

import java.io.Serializable

class AuctionNewHighBid: Serializable {
    lateinit var ItemId: String
    lateinit var SellerUserId: String
    lateinit var FormerTopBidder: String
    lateinit var NewTopBidder: String
}