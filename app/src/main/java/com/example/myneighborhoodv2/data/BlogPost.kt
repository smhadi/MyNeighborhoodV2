package com.example.myneighborhoodv2.data

data class BlogPost(
    val id: String,
    val postTitle: String,
    val postDetails: String,
    val postDate: String
){
    constructor(): this("", "", "", "")

}
