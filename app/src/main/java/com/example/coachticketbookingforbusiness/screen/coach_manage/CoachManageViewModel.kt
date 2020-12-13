package com.example.coachticketbookingforbusiness.screen.coach_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Coach
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.coach.CoachRepository
import com.example.coachticketbookingforbusiness.repository.coach.ICoachRepository

class CoachManageViewModel : BaseViewModel() {

    private val mCoachRepository: ICoachRepository by lazy {
        CoachRepository.getInstance(RetrofitClient.getAPIService())
    }

    val coachLiveData = MutableLiveData<List<Coach>>(arrayListOf())

    fun getCoach() {
        mCoachRepository.getCoach()
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { data, err ->
                if (err == null) {
                    coachLiveData.value = data
                }
            }
            .addToCompositeDisposable(disposable)
    }

}