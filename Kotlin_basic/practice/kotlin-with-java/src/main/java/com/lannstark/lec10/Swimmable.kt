package com.lannstark.lec10

interface Swimmable {

    val swimAbility: Int
        get() = 3
    fun act() {         // default method
        println("어푸어푸")
    }

}