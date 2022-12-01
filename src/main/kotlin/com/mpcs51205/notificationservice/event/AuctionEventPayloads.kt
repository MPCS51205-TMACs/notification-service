package com.mpcs51205.notificationservice.event

import java.io.Serializable

class AuctionEventPayloads {
}

class AuctionBid: Serializable {
    lateinit var bid_id: String
    lateinit var item_id: String
    lateinit var bidder_user_id: String
    lateinit var time_received: String
    var amount_in_cents: Int? = 0
    var active: Boolean? = null
}

class AuctionCancellation: Serializable {
    var timeReceived: String? = null
}

class AuctionEnd: Serializable {
    lateinit var Item: AuctionItem
    var Bids: List<AuctionItem> = listOf()
    lateinit var Cancellation: AuctionCancellation
    lateinit var WinningBid: AuctionBid
}

class AuctionItem: Serializable {
    lateinit var item_id: String
    lateinit var seller_user_id: String
    lateinit var start_time: String
    lateinit var end_time: String
    var start_price_in_cents: Int? = 0
}

class AuctionNewHighBid: Serializable {
    lateinit var ItemId: String
    lateinit var SellerUserId: String
    lateinit var FormerTopBidder: String
    lateinit var NewTopBidder: String
}

class AuctionStartOrEndSoon: Serializable {
    lateinit var itemid: String
    lateinit var selleruserid: String
    lateinit var starttime: String
    lateinit var endtime: String
    var startpriceincents: Int? = 0
    var TopBid: AuctionBid? = null
}