package com.example.coachticketbookingforbusiness.screen.profile

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.user.UserRepository

class ProfileViewModel : BaseViewModel() {

    private val mUserRepository by lazy {
        UserRepository.getInstance(RetrofitClient.getAPIService())
    }

    val user = MutableLiveData<User>()
    val isUpdateComplete = MutableLiveData<Boolean>()

    fun getProfile(phoneNumber: String) {
        mUserRepository.getUser(phoneNumber)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { user, err ->
                if (err == null) {
                    this.user.value = user
                } else {
                    mError.value = err.message
                }
            }
            .addToCompositeDisposable(disposable)
    }

    fun update(user: User, phoneNumber: String) {
        mUserRepository.updateUser(user, phoneNumber)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                isUpdateComplete.value = true
            }, {
                isUpdateComplete.value = false
            })
            .addToCompositeDisposable(disposable)
    }


    fun update(user: User, pass: String, phoneNumber: String) {
        mUserRepository.updateUserWithPassword(user, pass, phoneNumber)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                isUpdateComplete.value = true
            }, {
                isUpdateComplete.value = false
            })
            .addToCompositeDisposable(disposable)
    }
}