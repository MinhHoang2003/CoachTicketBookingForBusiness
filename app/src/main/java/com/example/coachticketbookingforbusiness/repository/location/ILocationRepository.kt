package com.example.coachticketbookingforbusiness.repository.location

import com.example.coachticketbookingforbusiness.model.Location
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ILocationRepository {
    fun getPickLocation(routeId: Int): Single<List<Location>>
    fun getDestination(routeId: Int): Single<List<Location>>
    fun getPickLocationWithTickets(routeId: Int, date : String) : Single<List<Location>>
    fun getDestinationLocationWithTickets(routeId: Int, date : String) : Single<List<Location>>
    fun getLocationByRouteId(id : Int) : Single<List<Location>>
    fun getLocation(id : Int) : Single<Location>
    fun updateLocation(location: Location): Completable
    fun addLocation(location: Location): Completable
}