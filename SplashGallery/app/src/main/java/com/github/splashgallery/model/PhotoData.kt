package com.github.splashgallery.model

data class PhotoData(val id : String, val urls : Urls)

data class Urls(
    val regular: String,
    val thumb: String
)