package com.example.coachticketbookingforbusiness.utils

import com.example.coachticketbookingforbusiness.model.LocalPosition
import io.reactivex.rxjava3.core.Single

interface IFileManager {

    companion object {
        const val POSITION_PATH = "position.json"
    }

    fun readPositionLocal(): Single<LocalPosition>

}