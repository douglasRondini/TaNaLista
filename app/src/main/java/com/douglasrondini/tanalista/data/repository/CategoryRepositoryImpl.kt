package com.douglasrondini.tanalista.data.repository

import com.douglasrondini.tanalista.data.local.CategoryDao
import com.douglasrondini.tanalista.domain.model.Category
import com.douglasrondini.tanalista.domain.model.toDomain
import com.douglasrondini.tanalista.domain.model.toEntity
import com.douglasrondini.tanalista.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(private val dao: CategoryDao): CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories().map { categories ->
            categories.map { it.toDomain() }
        }
    }

    override suspend fun insertCategory(category: Category) = dao.insertCategory(category.toEntity())

}