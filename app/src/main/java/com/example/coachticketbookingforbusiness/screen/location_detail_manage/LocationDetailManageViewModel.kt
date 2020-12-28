package com.example.coachticketbookingforbusiness.screen.location_detail_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.DebugLog
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.APIError
import com.example.coachticketbookingforbusiness.model.Location
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.location.ILocationRepository
import com.example.coachticketbookingforbusiness.repository.location.LocationRepository
import com.google.gson.Gson
import retrofit2.HttpException

class LocationDetailManageViewModel : BaseViewModel() {

    private val mLocationRepository: ILocationRepository by lazy {
        LocationRepository.getInstance(RetrofitClient.getAPIService())
    }

    val locationLiveData = MutableLiveData<Location>()
    val updateLiveData = MutableLiveData<Boolean>(false)

    fun getLocationDetail(id: Int) {
        mLocationRepository.getLocation(id)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { data, err ->
                if (err == null) {
                    locationLiveData.value = data
                } else {
                    mError.value = err.cause.toString()
                }
            }
            .addToCompositeDisposable(disposable)
    }

    fun updateLocation(location: Location) {
        mLocationRepository.updateLocation(location)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                updateLiveData.value = true
            }, {
                if (it is HttpException) {
//                    val message = Gson().fromJson(
//                        it.response()?.errorBody()?.charStream()?.readText(),
//                        APIError::class.java)

                    DebugLog.e("Hoang on error : ${ it.response()?.errorBody()?.charStream()?.readText()}")
                }
                updateLiveData.value = false
            }
            )
            .addToCompositeDisposable(disposable)
    }

    fun addLocation(location: Location) {
        mLocationRepository.addLocation(location)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                updateLiveData.value = true
            }, {
                if (it is HttpException) {
//                    val message = Gson().fromJson(
//                        it.response()?.errorBody()?.charStream()?.readText(),
//                        APIError::class.java)

                    DebugLog.e(
                        "Hoang on error : ${
                            it.response()?.errorBody()?.charStream()?.readText()
                        }"
                    )
                }
                updateLiveData.value = false
            }
            )
            .addToCompositeDisposable(disposable)
    }

}