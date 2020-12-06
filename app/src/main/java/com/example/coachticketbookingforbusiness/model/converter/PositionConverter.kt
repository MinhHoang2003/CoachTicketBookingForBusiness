package com.example.coachticketbookingforbusiness.model.converter

import com.example.coachticketbookingforbusiness.model.Position

object PositionConverter {
    fun convertLocalPositionToPosition(localPosition: List<String>): List<Position> {
        return localPosition.map { sitCode ->
            Position(hasPaid = 0, positionCode = sitCode)
        }
    }
}