package com.example.coachticketbookingforbusiness.repository.chooose_position

import com.example.coachticketbookingforbusiness.model.Position
import io.reactivex.rxjava3.core.Single

interface IChoosePositionRepository {
    fun getPositionOfRoute(routeId: Int, date: String): Single<List<Position>>
}