package com.example.coachticketbookingforbusiness.screen.coach_detail_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Coach
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.coach.CoachRepository
import com.example.coachticketbookingforbusiness.repository.coach.ICoachRepository

class CoachDetailManageViewModel : BaseViewModel() {
    private val mCoachRepository: ICoachRepository by lazy {
        CoachRepository.getInstance(RetrofitClient.getAPIService())
    }

    val coachLiveData = MutableLiveData<Coach>()
    val updateResultLiveDate = MutableLiveData<Boolean>(false)

    fun getCoach(id: String) {
        mCoachRepository.getCoachById(id)
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

    fun update(id: String, coach: Coach) {
        mCoachRepository.update(id, coach)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                updateResultLiveDate.value = true
            }, {
                updateResultLiveDate.value = false
                mError.value = it.message
            })
            .addToCompositeDisposable(disposable)
    }


    fun add(coach: Coach) {
        mCoachRepository.add(coach)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                updateResultLiveDate.value = true
            }, {
                updateResultLiveDate.value = false
                mError.value = it.message
            })
            .addToCompositeDisposable(disposable)
    }



}