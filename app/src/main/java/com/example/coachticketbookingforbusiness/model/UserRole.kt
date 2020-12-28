package com.example.coachticketbookingforbusiness.model

enum class UserRole(val value: Int) {
    USER(0),
    DRIVER(1),
    ADMIN(2);

    companion object {
        fun valueOf(value: Int): UserRole? = UserRole.values().find { it.value == value }
    }
}