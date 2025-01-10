package com.lannstark.lec04

fun main() {
    val money1 = JavaMoney(2000L)
    val money2 = JavaMoney(1000L)

    if (money1 > money2) {      // compareTo 호출
        println("ok")
    }


    val money3 = JavaMoney(1000)
    val money4 = money3
    val money5 = JavaMoney(1000)

    println(money3 === money4)      // 동등성 (Java ==)
    println(money3 == money5)       // equals 호출


    val money6 = Money(1000)
    val money7 = Money(2000)
    println(money1 + money2)        // plus  호출
}


fun fun1() : Boolean {
    println("fun 1")
    return true
}

fun fun2() :Boolean {
    println("fun2")
    return false
}