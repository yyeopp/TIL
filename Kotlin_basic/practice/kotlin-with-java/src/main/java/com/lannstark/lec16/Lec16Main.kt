package com.lannstark.lec16

fun main() {
    val str = "ABC"
    println(str.lastChar())     // 원래 String에 있었던 함수였던 것처럼 쓸 수 있음

    val person = Person("성엽", "강", 29)
    person.nextYearAge()
}

fun String.lastChar(): Char {           // 확장하려는 클래스를 확장하는, 확장함수
    return this[this.length - 1]        // this = 수신객체
}

fun Person.nextYearAge(): Int {     // 멤버 함수 시그니처 동일
    println("확장 함수")        // 호출되지 않음
    return this.age + 1
}