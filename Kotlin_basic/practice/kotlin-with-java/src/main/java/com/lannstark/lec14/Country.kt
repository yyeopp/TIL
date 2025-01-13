package com.lannstark.lec14

fun handleCountry(country: Country) {
    when (country) {        // else 필요 없음
        Country.KOREA -> TODO()
        Country.AMERICA -> TODO()
    }
}
enum class Country(
    private val country: String
) {
    KOREA("KO"),
    AMERICA("US")
}