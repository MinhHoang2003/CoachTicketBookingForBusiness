package com.example.coachticketbookingforbusiness.screen.location

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.DebugLog
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Location
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.location.LocationRepository

class LocationViewModel : BaseViewModel() {

    private val mLocationRepository by lazy {
        LocationRepository.getInstance(RetrofitClient.getAPIService())
    }

    val pickLocationLiveData: MutableLiveData<List<Location>> = MutableLiveData(arrayListOf())
    val destinationLocationLiveData: MutableLiveData<List<Location>> =
        MutableLiveData(arrayListOf())

    fun getPickLocationWithTickets(routeId: Int, date: String) {
        mLocationRepository.getPickLocationWithTickets(routeId, date)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { locations, err ->
                if (err == null) {
                    pickLocationLiveData.value = locations
                } else mError.value = err.message
            }.addToCompositeDisposable(disposable)
    }

    fun getDestinationLocationOfRoute(routeId: Int, date: String) {
        mLocationRepository.getDestinationLocationWithTickets(routeId, date)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { locations, err ->
                if (err == null) {
                    destinationLocationLiveData.value = locations
                } else mError.value = err.message
            }.addToCompositeDisposable(disposable)
    }


}