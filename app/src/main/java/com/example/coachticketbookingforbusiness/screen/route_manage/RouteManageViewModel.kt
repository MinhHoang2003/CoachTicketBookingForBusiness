package com.example.coachticketbookingforbusiness.screen.route_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Route
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.route.IRouteRepository
import com.example.coachticketbookingforbusiness.repository.route.RouteRepository

class RouteManageViewModel : BaseViewModel() {

    private val mRouteRepository: IRouteRepository by lazy {
        RouteRepository.getInstance(RetrofitClient.getAPIService())
    }

    val routeLiveData = MutableLiveData<List<Route>>()


    fun getRoutes() {
        mRouteRepository.getRoutes()
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { routes, err ->
                if (err == null) {
                    routeLiveData.value = routes
                }
            }.addToCompositeDisposable(disposable)
    }

}