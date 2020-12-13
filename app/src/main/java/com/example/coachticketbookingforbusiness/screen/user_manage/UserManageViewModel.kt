package com.example.coachticketbookingforbusiness.screen.user_manage

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.user.IUserRepository
import com.example.coachticketbookingforbusiness.repository.user.UserRepository

class UserManageViewModel : BaseViewModel() {

    private val mUserRepository: IUserRepository by lazy {
        UserRepository.getInstance(RetrofitClient.getAPIService())
    }
    val userLiveData = MutableLiveData<List<User>>()

    fun getUsers(role: Int) {
        mUserRepository.getUser(role)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { data, err ->
                if (err == null) {
                    userLiveData.value = data
                } else {
                    mError.value = err.message
                }
            }
            .addToCompositeDisposable(disposable)
    }

}