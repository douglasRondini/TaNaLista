package com.douglasrondini.tanalista.util

import androidx.room.RoomOpenDelegate

object FieldValidator {

    fun validateName(name: String?): ValidationResult {
        return  if(name.isNullOrBlank()) {
            ValidationResult.Error("Informe o nome do produto")

        } else {
            ValidationResult.Success
        }
    }

    fun validateCategory(category: String?): ValidationResult {
        return if(category.isNullOrBlank()) {
            ValidationResult.Error("Selecione uma categoria")
        } else {
            ValidationResult.Success
        }
    }

    fun validateQuantity(quantity: String?): ValidationResult {
        val quantity = quantity?.toIntOrNull()
        return if (quantity == null || quantity <= 0 ) {
            ValidationResult.Error("Quantidade inválida")
        } else {
            ValidationResult.Success
        }
    }

    fun validatePrice(priceText: String?): ValidationResult {
        val normalized = priceText?.replace(",",".")
        val price = normalized?.toDoubleOrNull()

        return if (price == null || price < 0.0) {
            ValidationResult.Error("Preço Inválido")
        } else {
            ValidationResult.Success
        }
    }

}