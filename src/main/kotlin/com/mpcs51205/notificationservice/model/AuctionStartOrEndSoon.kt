package com.mpcs51205.notificationservice.model

class AuctionStartOrEndSoon {
    lateinit var itemid: String
    lateinit var selleruserid: String
    lateinit var starttime: String
    lateinit var endtime: String
    var startpriceincents: Int? = 0
    var TopBid: AuctionBid? = null
}