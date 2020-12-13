package com.example.coachticketbookingforbusiness.repository.user

import com.example.coachticketbookingforbusiness.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IUserRepository {
    fun register(user: User): Completable
    fun login(username: String, password: String): Single<List<User>>
    fun getUser(role: Int): Single<List<User>>
    fun getUser(phoneNumber: String): Single<User>
    fun updateUser(user: User, phoneNumber: String): Completable
}