package com.example.coachticketbookingforbusiness.networking

import com.example.coachticketbookingforbusiness.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface APIService {

    // User
    @POST("users/register/")
    fun register(@Body user: User): Completable

    @POST("users/login/")
    fun login(@Body userLoginInformation: UserLoginInformation): Single<User>

    @GET("users/")
    fun getAllUserByRole(@Query("role") role: Int): Single<List<User>>

    @GET("users/detail")
    fun getUser(@Query("id") id: String): Single<User>

    @PUT("users/update")
    fun updateUser(@Body user: User, @Query("id") phoneNumber: String): Completable

    @PUT("users/updateWithPassword")
    fun updateUserWithPassword(
        @Body user: User,
        @Query("password") pass: String,
        @Query("id") phoneNumber: String
    ): Completable

    //Route

    @GET("routes/get")
    fun searchRoute(
        @Query("phone_number") phoneNumber: String,
        @Query("date") date: String
    ): Single<List<Route>>

    @GET("routes/position")
    fun getPosition(@Query("route_id") id: Int, @Query("date") date: String): Single<List<Position>>

    @GET("routes/")
    fun getRoutes(): Single<List<Route>>

    @GET("routes/{id}")
    fun getRoute(@Path("id") id: Int): Single<Route>

    @PUT("routes/{id}")
    fun updateRoute(@Body route: RouteBody, @Path("id") id: Int): Completable

    @POST("routes/add")
    fun add(@Body route: RouteBody): Single<Int>

    @POST("routes/remove")
    fun remove(@Query("id") id: Int): Completable

    // Location

    @PUT("locations/update")
    fun updateLocation(@Body location: Location): Completable

    @PUT("locations/add")
    fun addLocation(@Body location: Location): Completable

    @PUT("locations/remove")
    fun removeLocation(@Query("id") id: Int): Completable

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

    @GET("locations/routes/")
    fun getLocationByRouteId(@Query("route_id") id: Int): Single<List<Location>>

    @GET("locations/{id}")
    fun getLocation(@Path("id") id: Int): Single<Location>

    //Ticket
    @POST("tickets/")
    fun createTicket(@Body ticket: TicketLocalModel): Single<String>

    @GET("tickets/forDriver")
    fun getTicketsForRoute(
        @Query("id") id: Int,
        @Query("date") date: String
    ): Single<List<TicketDetail>>

    @GET("tickets/detail")
    fun getTicketDetail(@Query("id") id: Int): Single<TicketDetail>

    @GET("tickets/check")
    fun checkTicket(@Query("id") id: Int, @Query("date") date: String): Single<TicketDetail>

    // Coach
    @GET("coach/")
    fun getAllCoach(): Single<List<Coach>>

    @GET("coach/{id}")
    fun getCoach(@Path("id") id: String): Single<Coach>

    @PUT("coach/update")
    fun update(@Query("coachId") id: String, @Body coach: Coach): Completable

    @POST("coach/add")
    fun add(@Body coach: Coach): Completable

    @PUT("coach/remove")
    fun remove(@Query("id") id: String): Completable
}