package com.example.myneighborhoodv2.data

data class User(
    val email: String ="",
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val imagePath: String = ""
){
    constructor(): this("", "", "", "")
}
