package com.example.coachticketbookingforbusiness.screen.authentication.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.user.UserRepository
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.utils.SharePreferenceUtils

class LoginViewModel(private val context: Context) : BaseViewModel() {

    private val userRepository by lazy {
        UserRepository(RetrofitClient.getAPIService())
    }

    val loginResultLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun login(phoneNumber: String, password: String) {
        userRepository.login(phoneNumber, password)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { users, err ->
                if (users.isNotEmpty()) {
                    val user = users[0]
                    SharePreferenceUtils.saveUserData(context, user)
                    loginResultLiveData.value = true
                }
            }.addToCompositeDisposable(disposable)
    }

}