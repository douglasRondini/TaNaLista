package com.douglasrondini.tanalista.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.tanalista.domain.model.Category
import com.douglasrondini.tanalista.domain.usecases.GetAllCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories : StateFlow<List<Category>> = _categories

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


}