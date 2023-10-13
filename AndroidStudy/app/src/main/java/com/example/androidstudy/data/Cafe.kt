package com.example.androidstudy.data

enum class Temperature {
    Hot, Ice
}

enum class CoffeeOption {
    Vanilla, Almond, None
}

abstract class Menu {
    abstract val menuName: String
    abstract val price: Int
    abstract fun getMenu()
}

abstract class Coffee : Menu() {

    protected var optionTemperature: Temperature = Temperature.Hot

    fun setTemperature(temperature: Temperature) {
        optionTemperature = temperature
    }

    override fun getMenu() {
        println("${price}원 ${optionTemperature.name} $menuName 나왔습니다!")
    }
}

class Americano : Coffee() {

    override val price = 4000
    override val menuName = "아메리카노"
    private var coffeeOption: CoffeeOption = CoffeeOption.None

    override fun getMenu() {
        println("${price}원 $coffeeOption $optionTemperature $menuName 나왔습니다!")
    }

    fun addVanilla(option: CoffeeOption) {
        coffeeOption = option
    }

    fun addAlmond(option: CoffeeOption) {
        coffeeOption = option
    }
}

class Latte : Coffee() {

    override val price = 4500
    override val menuName = "카페라떼"
}

class VanillaLatte : Coffee() {

    override val price = 5000
    override val menuName = "바닐라라떼"
}

class VanillaCreamFrappuccino : Coffee() {
    override val price = 6000
    override val menuName = "바닐라크림프라푸치노"
}

interface GrapeFruit

class GrapeFruitAde : Menu(), GrapeFruit {
    override val menuName = "자몽에이드"
    override val price: Int = 4500
    override fun getMenu() {
        println("${price}원 $menuName 나왔습니다!")
    }
}

class GrapeFruitHoneyTea : Menu(), GrapeFruit {
    override val menuName = "자몽허니블랙티"
    override val price: Int = 4500
    override fun getMenu() {
        println("${price}원 $menuName 나왔습니다!")
    }
}