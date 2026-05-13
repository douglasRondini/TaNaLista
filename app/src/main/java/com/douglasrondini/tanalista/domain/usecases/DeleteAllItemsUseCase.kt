package com.douglasrondini.tanalista.domain.usecases

import com.douglasrondini.tanalista.domain.repository.ItemRepository

class DeleteAllItemsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke() {
        repository.deleteAllItems()
    }
}