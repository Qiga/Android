package com.example.androidstudy.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class MenuData() : Parcelable {

    abstract val name: String
    abstract val price: Int

    @Parcelize
    data class CoffeeData(
        override val name: String,
        override val price: Int,
        val temperature: Temperature,
        val option: CoffeeOption
    ) : MenuData()

    @Parcelize
    data class Beverage(
        override val name: String,
        override val price: Int,
    ) : MenuData()

}