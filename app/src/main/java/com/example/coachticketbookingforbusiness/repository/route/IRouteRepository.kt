package com.example.coachticketbookingforbusiness.repository.route

import com.example.coachticketbookingforbusiness.model.Route
import com.example.coachticketbookingforbusiness.model.RouteBody
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IRouteRepository {
    fun searchRoutes(phoneNumber: String, date: String): Single<List<Route>>
    fun getRoutes(): Single<List<Route>>
    fun getRoute(id: Int): Single<Route>
    fun updateRoute(route: RouteBody, id: Int): Completable
    fun add(route: RouteBody): Single<Int>
}