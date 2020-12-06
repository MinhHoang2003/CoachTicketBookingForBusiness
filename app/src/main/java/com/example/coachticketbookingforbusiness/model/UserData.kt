package com.example.coachticketbookingforbusiness.model

import com.example.coachticketbookingforbusiness.utils.Constants
import com.example.coachticketbookingforbusiness.utils.Utils

object UserData {
    var route: Route? = null
    var date: String = Constants.EMPTY_STRING
    var position: MutableList<Position> = mutableListOf()
    var pickLocation: Location? = null
    var destination: Location? = null
    var price: Int = 0

    fun resetData() {
        position = mutableListOf()
        pickLocation = null
        destination = null
        price = 0
    }

    fun getPositionCode(): String {
        val code = position.map { it.positionCode }.toString()
        return code.substring(1, code.toString().length - 1)
    }

    fun getDateConverted(): String {
        return Utils.getServerDateFormat(date)
    }
}