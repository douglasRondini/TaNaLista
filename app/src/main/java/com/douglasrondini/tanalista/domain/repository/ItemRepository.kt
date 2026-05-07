package com.douglasrondini.tanalista.domain.repository

import com.douglasrondini.tanalista.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun getItensByCategory(category: String): Flow<List<Item>>
    fun getAllItens(): Flow<List<Item>>
    suspend fun insertItem(item: Item)
    suspend fun deleteItem(item: Item)

}