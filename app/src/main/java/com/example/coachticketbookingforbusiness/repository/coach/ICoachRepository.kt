package com.example.coachticketbookingforbusiness.repository.coach

import com.example.coachticketbookingforbusiness.model.Coach
import com.example.coachticketbookingforbusiness.model.Location
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ICoachRepository {
    fun getCoach(): Single<List<Coach>>
    fun getCoachById(id: String): Single<Coach>
    fun update(id: String, coach: Coach): Completable
    fun add(coach: Coach): Completable
    fun remove(id: String): Completable
}