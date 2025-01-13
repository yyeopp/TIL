package com.lannstark.lec11

class Car(
    val name: String,
    var owner: String,
    _price: Int
) {
    var price = _price
        private set
}