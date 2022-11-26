package com.mpcs51205.notificationservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*
import javax.persistence.*

class ItemCategory(categoryName: String?): Serializable {
    var id: UUID? = null
    var categoryDescription: String? = categoryName
    var items = mutableListOf<Item>()
}