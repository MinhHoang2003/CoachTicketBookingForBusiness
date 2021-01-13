package com.example.coachticketbookingforbusiness.repository.user


import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.model.UserLoginInformation
import com.example.coachticketbookingforbusiness.networking.APIService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRepository(private val apiService: APIService) : IUserRepository {

    companion object {
        private var INSTANCE: IUserRepository? = null

        fun getInstance(apiService: APIService): IUserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService).also { INSTANCE = it }
            }

    }

    override fun register(user: User): Completable = apiService.register(user)

    override fun login(username: String, password: String): Single<User> {
        val userLoginInformation = UserLoginInformation(username, password)
        return apiService.login(userLoginInformation)
    }

    override fun getUser(role: Int): Single<List<User>> = apiService.getAllUserByRole(role)
    override fun getUser(phoneNumber: String): Single<User> = apiService.getUser(phoneNumber)
    override fun updateUser(user: User, phoneNumber: String): Completable =
        apiService.updateUser(user, phoneNumber)

    override fun updateUserWithPassword(
        user: User,
        password: String,
        phoneNumber: String
    ): Completable = apiService.updateUserWithPassword(user, password, phoneNumber)

}