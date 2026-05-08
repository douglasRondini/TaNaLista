package com.douglasrondini.tanalista.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.tanalista.domain.model.Item
import com.douglasrondini.tanalista.domain.usecases.InsertItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductRegistrationViewModel(
    private val insertItemUseCase: InsertItemUseCase
): ViewModel() {

    private val _insertState = MutableStateFlow<Boolean?>(null)
    val insertState: StateFlow<Boolean?> = _insertState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

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

}