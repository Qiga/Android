package com.github.cafe.data


data class UnsplashResponse( val res : ArrayList<PhotoData>)
data class PhotoData(val id : String, val urls : Urls)

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)