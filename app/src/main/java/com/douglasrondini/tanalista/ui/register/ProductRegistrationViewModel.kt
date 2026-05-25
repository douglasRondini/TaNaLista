package com.douglasrondini.tanalista.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.tanalista.domain.model.Category
import com.douglasrondini.tanalista.domain.model.Item
import com.douglasrondini.tanalista.domain.usecases.GetAllCategoriesUseCase
import com.douglasrondini.tanalista.domain.usecases.InsertCategoryUseCase
import com.douglasrondini.tanalista.domain.usecases.InsertItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductRegistrationViewModel(
    private val insertItemUseCase: InsertItemUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase
): ViewModel() {

    private val _insertState = MutableStateFlow<Boolean?>(null)
    val insertState: StateFlow<Boolean?> = _insertState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    fun insertItem(item: Item) {
        viewModelScope.launch {
            try {
                insertItemUseCase(item)
                _insertState.value = true
            } catch (e: Exception) {
                _insertState.value = false
                _errorState.value = e.message
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
           try {
               getAllCategoriesUseCase().collect { result ->
                   _categories.value = result
               }
           } catch (e: Exception) {
               _errorState.value = e.message
           }
        }
    }

    fun addCategory(name: String) {
        viewModelScope.launch {
            try {
                insertCategoryUseCase(Category(name = name))
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun clearInsertState() {
        _insertState.value = null
    }


}