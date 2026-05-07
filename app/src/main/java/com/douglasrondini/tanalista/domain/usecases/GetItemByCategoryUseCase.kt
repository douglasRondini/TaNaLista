package com.douglasrondini.tanalista.domain.usecases

import com.douglasrondini.tanalista.domain.repository.ItemRepository

class GetItemByCategoryUseCase(private val repository: ItemRepository) {
    operator fun invoke(category: String) = repository.getItensByCategory(category)
}