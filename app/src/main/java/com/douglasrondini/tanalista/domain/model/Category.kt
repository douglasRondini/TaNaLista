package com.douglasrondini.tanalista.domain.model

import com.douglasrondini.tanalista.data.local.CategoryEntity

data class Category(
    val id: Int = 0,
    val name: String

)
fun CategoryEntity.toDomain() = Category(id, name)
fun Category.toEntity() = CategoryEntity(id, name)


