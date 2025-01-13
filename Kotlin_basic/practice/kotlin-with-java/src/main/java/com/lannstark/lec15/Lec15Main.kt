package com.lannstark.lec15

fun main() {
    val array = arrayOf(100, 200)
    for (i in array.indices) {
        println("$i in ${array[i]}")
    }

    array.plus(300)

    for ((idx, value) in array.withIndex()) {
        println("$idx in $value")
    }

    val numbers = listOf(100, 200)      // 불변 List 생성하는 팩토리메서드
    val emptyList = emptyList<Int>()

    printNumbers(numbers)
    println(numbers[0])

    val numbersMutable = mutableListOf(100, 200)    // 가변 List

    val numberSet = setOf(100, 200)
    val numberSetMutable = mutableSetOf(100, 200)

    val oldMap = mutableMapOf<Int, String>()        // 가변
    oldMap[1] = "MONDAY"        // key 접근 방식 주목
    oldMap[2] = "TUESDAY"

    mapOf(1 to "MONDAY", 2 to "TUESDAY")        // 불변

    val list1 : List<Int>
    val list2 : List<Int?>
    val list3 : List<Int?>?

}

private fun printNumbers(numbers: List<Int>) {

}