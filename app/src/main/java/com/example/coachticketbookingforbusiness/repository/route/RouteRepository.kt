package com.example.coachticketbookingforbusiness.repository.route

import com.example.coachticketbookingforbusiness.model.Route
import com.example.coachticketbookingforbusiness.model.RouteBody
import com.example.coachticketbookingforbusiness.networking.APIService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RouteRepository(private val apiService: APIService) : IRouteRepository {

    companion object {
        var INSTANCE: IRouteRepository? = null

        fun getInstance(apiService: APIService): IRouteRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RouteRepository(apiService).also { INSTANCE = it }
        }

    }

    override fun searchRoutes(
        phoneNumber: String,
        date: String
    ): Single<List<Route>> = apiService.searchRoute(phoneNumber, date)

    override fun getRoutes(): Single<List<Route>> = apiService.getRoutes()
    override fun getRoute(id: Int): Single<Route> = apiService.getRoute(id)
    override fun updateRoute(route: RouteBody, id: Int): Completable = apiService.updateRoute(
        route,
        id
    )
    override fun add(route: RouteBody): Single<Int> = apiService.add(route)
    override fun remove(id: Int): Completable = apiService.remove(id)
}