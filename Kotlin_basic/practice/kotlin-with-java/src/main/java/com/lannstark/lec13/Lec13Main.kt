package com.lannstark.lec13


fun main() {

}

class House(
    private val address: String,
    private val livingRoom: LivingRoom
) {
    class LivingRoom(          // static 중첩 클래스 (static 사용, 권장)
        private val area: Double
    )

    inner class LivingRoom2(       // 일반 중첩 클래스 (static 미사용, 비권장)
        private val area: Double
    ) {
        val address: String
            get() = this@House.address      // 외부 클래스 참조
    }
}