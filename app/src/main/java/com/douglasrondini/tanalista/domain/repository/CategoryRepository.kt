package com.douglasrondini.tanalista.domain.repository

import com.douglasrondini.tanalista.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun insertCategory(category: Category)
}


