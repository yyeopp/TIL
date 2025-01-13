package com.lannstark.lec10

class Penguin(
    species: String,
    private val wingCount: Int = 2
) : Animal(species, 2), Swimmable, Flyable {
//    private val wingCount: Int = 2
    override fun move() {
        println("Penguin Move")
    }

    override val legCount: Int
        get() = super.legCount + this.wingCount

    override fun act() {
        super<Swimmable>.act()
        super<Flyable>.act()
    }

    override val swimAbility: Int
        get() = 5
}