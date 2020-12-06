package com.example.coachticketbookingforbusiness.repository.user


import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.model.UserLoginInformation
import com.example.coachticketbookingforbusiness.networking.APIService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRepository(private val apiService: APIService) : IUserRepository {

    override fun getUserInformation(phoneNumber: String): Single<User> {
        return apiService.getUserInformation(phoneNumber).flatMap {
            Single.just(it.firstOrNull())
        }
    }

    override fun register(user: User): Completable = apiService.register(user)

    override fun login(username: String, password: String): Single<List<User>> {
        val userLoginInformation = UserLoginInformation(username, password)
        return apiService.login(userLoginInformation)
    }

}