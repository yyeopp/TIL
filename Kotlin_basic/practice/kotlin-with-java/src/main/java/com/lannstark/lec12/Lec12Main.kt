package com.lannstark.lec12

fun main() {
    moveSomething(object : Movable {            // 익명 내부 클래스, 인터페이스 구현
        override fun move() {
            println("move")
        }
        override fun fly() {
            println("fly")
        }
    })
}

class Person private constructor(
    var name: String,
    var age: Int
) {
    companion object Factory {      // 객체다 : 이름 지정 가능, 인터페이스 구현 가능
        private const val MIN_AGE = 1               // 컴파일 시 변수 할당 '진짜 상수'
        @JvmStatic      // Java 에서 static 접근 가능
        fun newBaby(name: String) : Person {
            return Person(name, MIN_AGE)
        }
    }
}

object Singleton {      // 싱글톤 만드는 방법
    var a: Int = 0
}

private fun moveSomething(movable: Movable) {
    movable.move()
    movable.fly()
}
