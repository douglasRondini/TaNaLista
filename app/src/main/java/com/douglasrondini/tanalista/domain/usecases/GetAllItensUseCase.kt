package com.douglasrondini.tanalista.domain.usecases

import com.douglasrondini.tanalista.domain.repository.ItemRepository

class GetAllItensUseCase(private val repository: ItemRepository) {
    operator fun invoke() = repository.getAllItens()
}