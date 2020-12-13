package com.example.coachticketbookingforbusiness.repository.coach

import com.example.coachticketbookingforbusiness.model.Coach
import com.example.coachticketbookingforbusiness.networking.APIService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class CoachRepository(private val apiService: APIService) : ICoachRepository {

    companion object {
        private var INSTANCE: ICoachRepository? = null

        fun getInstance(apiService: APIService): ICoachRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CoachRepository(apiService).also { INSTANCE = it }
            }

    }

    override fun getCoach(): Single<List<Coach>> = apiService.getAllCoach()
    override fun getCoachById(id: String): Single<Coach> = apiService.getCoach(id)
    override fun update(id: String, coach: Coach): Completable = apiService.update(id, coach)
    override fun add(coach: Coach): Completable = apiService.add(coach)
}