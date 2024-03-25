package com.example.myneighborhoodv2.util

import android.util.Patterns

fun validateEmail(email:String): RegisterValidation{
    if (email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty!")

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return  RegisterValidation.Failed("Wrong Email Format!")

    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation{
    if (password.isEmpty())
        return RegisterValidation.Failed("Password cannot be empty")

    if (password.length < 8)
        return RegisterValidation.Failed("Password should be of minimum length 8")

    return RegisterValidation.Success
}

fun confirmPassword(confirmPassword: String, password: String): RegisterValidation{
    if(password == confirmPassword)
        return RegisterValidation.Success
    else
        return RegisterValidation.Failed("Passwords do not match")
}

fun validateFName(FName: String): RegisterValidation {
    if(FName == "")
        return RegisterValidation.Failed("Name Cannot be Empty")
    else
        return RegisterValidation.Success

}fun validateLName(LName: String): RegisterValidation {
    if(LName == "")
        return RegisterValidation.Failed("Name Cannot be Empty")
    else
        return RegisterValidation.Success

}