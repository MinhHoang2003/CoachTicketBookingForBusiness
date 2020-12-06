package com.example.coachticketbookingforbusiness.repository.route

import com.example.coachticketbookingforbusiness.model.Route
import io.reactivex.rxjava3.core.Single

interface IRouteRepository {
    fun searchRoutes(phoneNumber: String, date: String): Single<List<Route>>
}