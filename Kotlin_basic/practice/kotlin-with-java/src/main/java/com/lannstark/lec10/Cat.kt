package com.lannstark.lec10

class Cat(
    species: String
) : Animal(species, 4) {            // extend, super 호출
    override fun move() {
        println("Cat Move")
    }
}