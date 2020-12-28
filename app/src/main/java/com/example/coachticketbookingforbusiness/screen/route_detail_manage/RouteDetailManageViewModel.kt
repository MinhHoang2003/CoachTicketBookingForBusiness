package com.example.coachticketbookingforbusiness.screen.route_detail_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.DebugLog
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Route
import com.example.coachticketbookingforbusiness.model.RouteBody
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.route.IRouteRepository
import com.example.coachticketbookingforbusiness.repository.route.RouteRepository

class RouteDetailManageViewModel : BaseViewModel() {

    private val mRouteRepository: IRouteRepository by lazy {
        RouteRepository.getInstance(RetrofitClient.getAPIService())
    }
    val routeLiveData = MutableLiveData<Route>()
    val updateResultLiveDate = MutableLiveData<Boolean>(false)
    val insertResultLiveDate = MutableLiveData<Int>(-1)

    fun getRoute(id: Int) {
        mRouteRepository.getRoute(id)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { data, err ->
                if (err == null) {
                    routeLiveData.value = data
                }
            }
            .addToCompositeDisposable(disposable)
    }

    fun update(id: Int, route: RouteBody) {
        mRouteRepository.updateRoute(route, id)
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

    fun add(route: RouteBody) {
        mRouteRepository.add(route)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                insertResultLiveDate.value = it
            }, {
                insertResultLiveDate.value = -1
                mError.value = it.message
            })
            .addToCompositeDisposable(disposable)
    }

}