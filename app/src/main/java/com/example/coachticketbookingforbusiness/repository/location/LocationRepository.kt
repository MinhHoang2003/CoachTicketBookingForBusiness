package com.example.coachticketbookingforbusiness.repository.location

import com.example.coachticketbookingforbusiness.model.Location
import com.example.coachticketbookingforbusiness.networking.APIService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocationRepository(private val apiService: APIService) : ILocationRepository {

    companion object {
        private var INSTANCE: ILocationRepository? = null

        fun getInstance(apiService: APIService): ILocationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationRepository(apiService).also { INSTANCE = it }
            }

    }

    override fun getPickLocation(routeId: Int): Single<List<Location>> =
        apiService.getPickLocation(routeId)

    override fun getDestination(routeId: Int): Single<List<Location>> =
        apiService.getDestinationLocation(routeId)

    override fun getPickLocationWithTickets(routeId: Int, date: String): Single<List<Location>> =
        apiService.getPickLocationWithTickets(routeId, date)

    override fun getDestinationLocationWithTickets(
        routeId: Int,
        date: String
    ): Single<List<Location>> = apiService.getDestinationLocationWithTickets(routeId, date)

    override fun getLocationByRouteId(id: Int): Single<List<Location>> =
        apiService.getLocationByRouteId(id)

    override fun getLocation(id: Int): Single<Location> = apiService.getLocation(id)
    override fun updateLocation(location: Location): Completable =
        apiService.updateLocation(location)

    override fun addLocation(location: Location): Completable = apiService.addLocation(location)
}