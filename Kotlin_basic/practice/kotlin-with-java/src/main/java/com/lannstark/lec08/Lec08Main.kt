package com.lannstark.lec08

fun main() {
    repeat("Hello World")
    repeat("Hello World", useNewLine = false)       // named parameter
    printNameAndGender(name = "강성엽", gender = "Male")   // Builder 유사

    printAll("A", "B", "C")

    val array = arrayOf("A", "B", "C")
    printAll(*array)        // spread 연산자
}

fun max(a: Int, b: Int) = if (a > b) a else b
// 타입 추론, 중괄호 생략

fun repeat(
    str: String,
    num: Int = 3,                   // default parameter, nullable
    useNewLine: Boolean = true
) {
    for (i in 1..num) {
        if (useNewLine) {
            println(str)
        } else {
            print(str)
        }
    }
}

fun printNameAndGender(name: String, gender: String) {
    println(name)
    println(gender)
}

fun printAll(vararg strings: String) {      // vararg
    for (str in strings) {
        println(str)
    }
}