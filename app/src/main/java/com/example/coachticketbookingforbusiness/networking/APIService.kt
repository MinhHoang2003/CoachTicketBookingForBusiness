package com.example.coachticketbookingforbusiness.networking

import com.example.coachticketbookingforbusiness.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface APIService {

    @POST("users/register/")
    fun register(@Body user: User): Completable

    @POST("users/login/")
    fun login(@Body userLoginInformation: UserLoginInformation): Single<List<User>>

    @GET("users/")
    fun getAllUserByRole(@Query("role") role: Int): Single<List<User>>

    @GET("users/detail")
    fun getUser(@Query("id") id: String): Single<User>

    @PUT("users/update")
    fun updateUser(@Body user: User, @Query("id") phoneNumber: String): Completable

    @GET("routes/get")
    fun searchRoute(
        @Query("phone_number") phoneNumber: String,
        @Query("date") date: String
    ): Single<List<Route>>

    @GET("routes/position")
    fun getPosition(@Query("route_id") id: Int, @Query("date") date: String): Single<List<Position>>

    @GET("locations/pick")
    fun getPickLocation(@Query("route_id") id: Int): Single<List<Location>>

    @GET("locations/destination")
    fun getDestinationLocation(@Query("route_id") id: Int): Single<List<Location>>

    @GET("locations/routes/pick")
    fun getPickLocationWithTickets(
        @Query("route_id") id: Int,
        @Query("date") date: String
    ): Single<List<Location>>

    @GET("locations/routes/destination")
    fun getDestinationLocationWithTickets(
        @Query("route_id") id: Int,
        @Query("date") date: String
    ): Single<List<Location>>

    @POST("tickets/")
    fun createTicket(@Body ticket: TicketLocalModel): Single<String>

    @GET("tickets/forDriver")
    fun getTicketsForRoute(
        @Query("id") id: Int,
        @Query("date") date: String
    ): Single<List<TicketDetail>>

    @GET("tickets/detail")
    fun getTicketDetail(@Query("id") id: Int): Single<TicketDetail>

    @GET("coach/")
    fun getAllCoach(): Single<List<Coach>>

    @GET("coach/{id}")
    fun getCoach(@Path("id") id: String): Single<Coach>

    @PUT("coach/update")
    fun update(@Query("coachId") id: String, @Body coach: Coach): Completable

    @POST("coach/add")
    fun add(@Body coach: Coach): Completable
}