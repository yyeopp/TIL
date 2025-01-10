package com.lannstark.lec03

fun main() {

    val number1 = 3
    val number2: Long = number1.toLong()

    val number3: Int? = 3
    val number4: Long = number3?.toLong() ?: 0L

    val person = Person("최태현", 100)
    println("이름 : ${person.name} / 나이 : ${person.age}")

    val str : String =
    """
        와 신기하다
        이게 그냥 막 된다고?
        ${person.name}
    """.trimIndent()

    println(str)

    var str2 = "ABC"
    println(str[0])



}


fun printAgeIfPerson(obj: Any) {
    if (obj is Person) {    // instanceof
//        val person = obj as Person  // (Person)
//        println(person.age)
        println(obj.age)
    }
    if (obj !is Person) {
    }
}

fun printAgeIfPerson2(obj: Any?) {
    val person = obj as? Person     // Nullable
    println(person?.age)
}