package com.douglasrondini.tanalista.util

sealed class ValidationResult {
    object Success: ValidationResult()
    data class Error(val message: String): ValidationResult()
}