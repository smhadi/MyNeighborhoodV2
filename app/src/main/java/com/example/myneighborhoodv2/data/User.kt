package com.example.myneighborhoodv2.data

data class User(
    val email: String ="",
    var firstName: String = "",
    var lastName: String = "",
    val gender: String = "",
    val imagePath: String = ""
){
    constructor(): this("", "", "", "")
    public fun updateUser(user: User,fName: String, lName: String){
        user.firstName = fName
        user.lastName = lName

    }
}
