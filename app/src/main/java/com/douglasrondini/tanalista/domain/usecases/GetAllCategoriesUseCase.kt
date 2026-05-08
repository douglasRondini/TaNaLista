package com.douglasrondini.tanalista.domain.usecases

import com.douglasrondini.tanalista.domain.repository.CategoryRepository

class GetAllCategoriesUseCase(private val repository: CategoryRepository) {
    operator fun invoke() = repository.getAllCategories()
}