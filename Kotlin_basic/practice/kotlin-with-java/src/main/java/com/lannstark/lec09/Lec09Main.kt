package com.lannstark.lec09

class Person(
    name: String,
    var age: Int
) {      // 주생성자, getter, setter

    var name = name
        get() = field.uppercase()           // 필드 자기 자신을 호출 (backing field)
        set(value) {
            field = value.uppercase()          // setter 지양
        }
    init {      // 초기화, validation
        if (age < 0) {
            throw IllegalArgumentException("${age} age")
        }
        println("초기화 블록")
    }

    constructor(name: String) : this(name, 100) {
        println("부생성자 1")
    }

    constructor() : this("강성엽") {
        println("부생성자 2")
    }

    fun isAdult(): Boolean {        // 클래스에 함수 추가
        return this.age >= 20
    }

    val isAdult2: Boolean
        get() = this.age >= 20      // custom getter 추가, 함수 블록 축약

    val isAdult3: Boolean
        get() {
            return this.age >= 20   // custom getter 추가
        }
}

fun main() {
    val person = Person("강성엽", 100)
    println(person.name)
    person.age = 15
    println(person.age)

    val javaPerson = JavaPerson("강성엽", 100)
    println(javaPerson.name)
    javaPerson.age = 15
    println(javaPerson.age)

    val person2 = Person("강성엽")
    val person3 = Person()
}