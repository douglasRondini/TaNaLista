package com.douglasrondini.tanalista.domain.usecases

import com.douglasrondini.tanalista.domain.model.Category
import com.douglasrondini.tanalista.domain.repository.CategoryRepository

class InsertCategoryUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(category: Category) = repository.insertCategory(category)
}