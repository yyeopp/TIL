package com.lannstark.lec04

data class Money(val amount: Long) {
    operator fun plus(other: Money) : Money {       // 연산자 오버로딩
        return Money(this.amount + other.amount)
    }
}