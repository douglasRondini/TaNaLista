package com.douglasrondini.tanalista.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.tanalista.domain.model.Category
import com.douglasrondini.tanalista.domain.model.Item
import com.douglasrondini.tanalista.domain.usecases.GetAllCategoriesUseCase
import com.douglasrondini.tanalista.domain.usecases.GetAllItensUseCase
import com.douglasrondini.tanalista.domain.usecases.GetItemByCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllItensUseCase: GetAllItensUseCase,
    private val getItemByCategoryUseCase: GetItemByCategoryUseCase

): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories : StateFlow<List<Category>> = _categories

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState


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

    fun lodItemsByCategory(categoryName: String) {
        viewModelScope.launch {
            getItemByCategoryUseCase(categoryName).collect { result ->
                _items.value = result
            }
        }
    }

    fun loadAllItems() {
        viewModelScope.launch {
            getAllItensUseCase().collect { result ->
                _items.value = result
            }
        }
    }


}