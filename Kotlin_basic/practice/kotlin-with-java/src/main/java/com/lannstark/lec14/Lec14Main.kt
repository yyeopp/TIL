package com.lannstark.lec14

fun main() {
    val dto1 = PersonDto("강성엽", 30)
    val dto2 = PersonDto("강성엽", 30)

    println(dto1 === dto2)
    println(dto1 == dto2)       // equals
}
data class PersonDto(       // equals, hashCode, toString 자동 생성
    val name: String,
    val age: Int
)