package com.douglasrondini.tanalista.data.repositoy

import com.douglasrondini.tanalista.data.local.ItemDao
import com.douglasrondini.tanalista.domain.model.Item
import com.douglasrondini.tanalista.domain.model.toDomain
import com.douglasrondini.tanalista.domain.model.toEntity
import com.douglasrondini.tanalista.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemRepositoryImpl(private val dao: ItemDao): ItemRepository {
    override fun getItensByCategory(category: String): Flow<List<Item>> {
        return dao.getItemsByCategory(category).map { items ->
            items.map { it.toDomain() }
        }
    }

    override fun getAllItens(): Flow<List<Item>> {
        return dao.getAllItems().map { items ->
            items.map { it.toDomain() }
        }
    }

    override suspend fun insertItem(item: Item) = dao.insertItem(item.toEntity())

    override suspend fun deleteItem(item: Item) = dao.deletItem(item.toEntity())
    override suspend fun updateItem(item: Item) = dao.updateItem(item.toEntity())
    override suspend fun deleteAllItems() = dao.deleteAllItems()

}