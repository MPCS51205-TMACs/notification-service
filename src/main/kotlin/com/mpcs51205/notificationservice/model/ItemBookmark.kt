package com.mpcs51205.notificationservice.model

import java.util.*


class ItemBookmark {
    var bookmarkId: UUID? = null
    var userBookmark: UUID? = null
    lateinit var bookmarkedItem: Item
}