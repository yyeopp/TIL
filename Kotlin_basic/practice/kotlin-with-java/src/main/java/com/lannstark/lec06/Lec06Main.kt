package com.lannstark.lec06

fun main() {
    val numbers = listOf(1, 2, 3)
    for (number in numbers) {       // Iterable
        println(number)
    }

    for (i in 1..3) {
        println(i)
    }

    for (i in 3 downTo 1) {     // 등차수열 생성
        println(i)
    }

    for (i in 1..5 step 2) {    // 중위 호출 함수
        println(i)
    }



}