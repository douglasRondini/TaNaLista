package com.douglasrondini.tanalista.domain.model

import com.douglasrondini.tanalista.data.local.ItemEntity

data class Item(
    val id: Int = 0,
    val name: String,
    val category: String,
    val quantity: Int,
    val price: Double,
    val checked: Boolean = false
)

// Mapper
fun ItemEntity.toDomain() = Item(id, name, category, quantity, price, checked)
fun Item.toEntity() = ItemEntity(id, name, category, quantity, price, checked)


