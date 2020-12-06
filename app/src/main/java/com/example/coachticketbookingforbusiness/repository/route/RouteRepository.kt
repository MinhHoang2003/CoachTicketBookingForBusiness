package com.example.coachticketbookingforbusiness.repository.route

import com.example.coachticketbookingforbusiness.model.Route
import com.example.coachticketbookingforbusiness.networking.APIService
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

}