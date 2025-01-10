package com.lannstark.lec05

fun validateScoreIsNotNegative(score: Int) {
    if (score !in 0..100) {             // in, ..
        throw IllegalArgumentException("FAIL")
    }
}

fun getPassOrFail(score: Int): String {     // if~else 는 expression
    return if (score >= 50) {
        "P"
    } else {
        "F"
    }
}

fun getGrade(score: Int): String {
    return if (score > 0) {
        "A"
    } else {
        "B"
    }
}

fun getGradeWithSwitch(score: Int): String {       // switch -> when
    return when (score) {
        in 90..100 -> "A"
        in 80..89 -> "B"
        in 70..79 -> "C"
        else -> "D"
    }
}

fun startsWithA(obj: Any): Boolean {
    return when (obj) {
        is String -> obj.startsWith("A")        // smart casting
        else -> false
    }
}

fun judgeNumber(number: Int) {
    when (number) {
        1, 0, -1 -> println("OK")
        else -> println("NO")
    }
}

fun judgeNumber2(number: Int) {
    when {
        number == 0 -> println("0")
        number % 2 == 0 -> println("짝수")
        else -> println("NO")
    }
}