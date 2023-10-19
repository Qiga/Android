package com.github.dalle.data

data class UserImg (val id: String, val urls : Urls)

data class Urls (
    val regular : String,
    val thumb : String
)