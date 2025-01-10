package com.lannstark.lec02

import java.lang.IllegalArgumentException

fun main() {

    var str: String? = "ABC"
    println(str?.length)

    var str2: String? = null
    println(str2?.length)

//    println(startsWithA4(null))

    val person = Person(null)
    startsWithA5(person.name)       // NPE

}

fun startsWithA5(str: String) : Boolean {
    return str.startsWith("A")
}

fun startsWithA(str: String) :Boolean {
    return str.startsWith("A")
}

fun startsWithA1(str: String?): Boolean {    // Nullable
    return str?.startsWith("A")
        ?: throw IllegalArgumentException("null")

//    if (str == null) {
//        throw IllegalArgumentException("null")
//    }
//    return str.startsWith("A")
}

fun startsWithA2(str: String?): Boolean? {
    return str?.startsWith("A")

//    if (str == null) {
//        return null
//    }
//    return str.startsWith("A")
}

fun startsWithA3(str: String?): Boolean {
    return str?.startsWith("A") ?: false

//    if (str == null) {
//        return false;
//    }
//    return str.startsWith("A")
}

fun startsWithA4(str: String?): Boolean {
    return str!!.startsWith("A")
}