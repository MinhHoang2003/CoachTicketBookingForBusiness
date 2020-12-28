package com.example.coachticketbookingforbusiness.model

enum class StopStationType(private val value: Int) {
    PICK(1),
    DROP(2);

    companion object {
        fun valueOf(value: Int): StopStationType? =
            StopStationType.values().find { it.value == value }
    }
}

fun StopStationType.convertToName(): String {
    return when (this) {
        StopStationType.PICK -> "Điểm đón"
        StopStationType.DROP -> "Điểm trả khách"
    }
}