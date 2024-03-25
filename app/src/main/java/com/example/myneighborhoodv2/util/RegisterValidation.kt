package com.example.myneighborhoodv2.util

sealed class RegisterValidation(){
    object Success: RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}

data class RegisterFieldsState(
    val email: RegisterValidation,
    val password: RegisterValidation,
    val confirmPassword: RegisterValidation,
    val firstName: RegisterValidation = RegisterValidation.Success,
    val lastName: RegisterValidation = RegisterValidation.Success
)
