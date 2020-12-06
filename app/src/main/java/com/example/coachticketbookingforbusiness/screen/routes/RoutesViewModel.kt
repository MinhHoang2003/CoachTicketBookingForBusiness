package com.example.coachticketbookingforbusiness.screen.routes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.DebugLog
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Route
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.route.IRouteRepository
import com.example.coachticketbookingforbusiness.repository.route.RouteRepository

class RoutesViewModel : BaseViewModel() {
    private val routeRepository: IRouteRepository by lazy {
        RouteRepository.getInstance(
            RetrofitClient.getAPIService()
        )
    }

    val routesLiveData: MutableLiveData<List<Route>> = MutableLiveData(listOf())

    fun searchRoutes( phoneNumber: String, date: String) {
        routeRepository.searchRoutes(phoneNumber, date)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                Log.e("Hoang", it.toString())
                routesLiveData.value = it
            }, {
                DebugLog.e("Hoang ${it.message}")
                mError.value = it.message
            }).addToCompositeDisposable(disposable)
    }
}
