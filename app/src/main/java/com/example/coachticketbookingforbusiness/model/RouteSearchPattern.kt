package com.example.coachticketbookingforbusiness.model

import android.os.Parcel
import android.os.Parcelable
import com.example.coachticketbookingforbusiness.utils.Constants

data class RouteSearchPattern(
    val pickLocation: String,
    val destination: String,
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: Constants.EMPTY_STRING,
        parcel.readString() ?: Constants.EMPTY_STRING,
        parcel.readString() ?: Constants.EMPTY_STRING
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pickLocation)
        parcel.writeString(destination)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RouteSearchPattern> {
        override fun createFromParcel(parcel: Parcel): RouteSearchPattern {
            return RouteSearchPattern(parcel)
        }

        override fun newArray(size: Int): Array<RouteSearchPattern?> {
            return arrayOfNulls(size)
        }
    }
}