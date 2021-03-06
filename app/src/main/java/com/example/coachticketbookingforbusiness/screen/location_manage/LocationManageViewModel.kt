package com.example.coachticketbookingforbusiness.screen.location_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Location
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.location.ILocationRepository
import com.example.coachticketbookingforbusiness.repository.location.LocationRepository

class LocationManageViewModel : BaseViewModel() {

    private val mLocationRepository: ILocationRepository by lazy {
        LocationRepository.getInstance(RetrofitClient.getAPIService())
    }

    val locationsLiveData = MutableLiveData<List<Location>>(arrayListOf())
    val isDeleted = MutableLiveData<Boolean>()
    fun getLocationByRouteId(id: Int) {
        mLocationRepository.getLocationByRouteId(id)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { locations, err ->
                if (err == null) {
                    locationsLiveData.value = locations
                } else {
                    mError.value = err.message
                }
            }
            .addToCompositeDisposable(disposable)
    }

    fun removeLocation(id: Int) {
        mLocationRepository.removeLocation(id)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                isDeleted.value = true
            }, {
                isDeleted.value = false
                mError.value = "Không thể xóa điểm dừng này."
            }).addToCompositeDisposable(disposable)
    }


}