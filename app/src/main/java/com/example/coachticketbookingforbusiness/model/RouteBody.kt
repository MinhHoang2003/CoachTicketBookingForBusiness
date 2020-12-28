package com.example.coachticketbookingforbusiness.model

import com.google.gson.annotations.SerializedName

data class RouteBody(
    val id: Int,
    @SerializedName("coach_id")
    val coachId: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("estimate_end_time")
    val endTime: String,
    val price: Int
)