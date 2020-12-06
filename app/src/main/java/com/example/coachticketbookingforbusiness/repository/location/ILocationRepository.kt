package com.example.coachticketbookingforbusiness.repository.location

import com.example.coachticketbookingforbusiness.model.Location
import io.reactivex.rxjava3.core.Single

interface ILocationRepository {
    fun getPickLocation(routeId: Int): Single<List<Location>>
    fun getDestination(routeId: Int): Single<List<Location>>
}