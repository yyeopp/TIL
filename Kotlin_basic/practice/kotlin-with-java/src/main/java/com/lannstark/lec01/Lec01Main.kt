package com.lannstark.lec01

fun main() {
    var number1 = 10L
    number1 = 5L
    val number2 = 10L       // final
//    number2 = 5L

    var number3: Long
//    println(number3)
    number3 = 5
    println(number3)

    val number4 :Long
    number4 = 5


    var number5 = 10L
    var number6 = 1_000L        // Reference Type ??

    var number7: Long? = 1_000L     // Nullable
    number7 = null


    var person = Person("강성엽")      // new

}