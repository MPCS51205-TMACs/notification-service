package com.mpcs51205.notificationservice.model

import java.text.Bidi
import java.util.*

/**
 * "SentStartSoonAlert": bool,
"SentEndSoonAlert": bool,
"Finalization": {
"time_received": string
},
"WinningBid": {
"bid_id": string,
"item_id": string,
"bidder_user_id": string,
"time_received": string,
"amount_in_cents": 10,
"active": bool
}

 */
class Auction {
    lateinit var Item: AuctionItem
    var Bids: List<AuctionItem> = listOf()
    lateinit var Cancellation: AuctionCancellation
    lateinit var WinningBid: AuctionBid
}