package com.douglasrondini.tanalista.domain.usecases

import com.douglasrondini.tanalista.domain.model.Item
import com.douglasrondini.tanalista.domain.repository.ItemRepository

class DeletItemUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(item: Item) = repository.deleteItem(item)
}