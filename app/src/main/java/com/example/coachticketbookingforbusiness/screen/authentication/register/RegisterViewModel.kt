package com.example.coachticketbookingforbusiness.screen.authentication.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.user.UserRepository
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.utils.SharePreferenceUtils

class RegisterViewModel(private val context: Context) : BaseViewModel() {

    private val mUserRepository by lazy {
        UserRepository(RetrofitClient.getAPIService())
    }

    val registerResultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun register(user: User) {
        mUserRepository.register(user)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                SharePreferenceUtils.saveUserData(context, user)
                registerResultLiveData.value = true
            }, {
                mError.value = it.message
            }).addToCompositeDisposable(disposable)
    }
}
