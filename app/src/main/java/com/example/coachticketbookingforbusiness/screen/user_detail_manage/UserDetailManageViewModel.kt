package com.example.coachticketbookingforbusiness.screen.user_detail_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.user.IUserRepository
import com.example.coachticketbookingforbusiness.repository.user.UserRepository

class UserDetailManageViewModel : BaseViewModel() {


    private val mUserRepository: IUserRepository by lazy {
        UserRepository.getInstance(RetrofitClient.getAPIService())
    }

    val userLiveData = MutableLiveData<User>()
    val updateResultLiveDate = MutableLiveData(false)
    fun getUser(phoneNumber: String) {
        mUserRepository.getUser(phoneNumber)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { data, err ->
                if (err == null) {
                    userLiveData.value = data
                }
            }.addToCompositeDisposable(disposable)
    }

    fun updateUser(user: User, phoneNumber: String) {
        mUserRepository.updateUser(user, phoneNumber)
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

    fun createUser(user: User) {
        mUserRepository.register(user)
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