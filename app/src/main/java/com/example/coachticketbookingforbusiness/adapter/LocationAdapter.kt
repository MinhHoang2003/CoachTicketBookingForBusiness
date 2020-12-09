package com.example.coachticketbookingforbusiness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.model.Location
import kotlinx.android.synthetic.main.layout_choose_location_item.view.*


class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private val mLocations = mutableListOf<Location>()

    fun setData(data: List<Location>) {
        mLocations.clear()
        mLocations.addAll(data)
        notifyDataSetChanged()
    }

    inner class LocationViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(location: Location) {
            itemView.textPosition.text = String.format("Điểm đón: %s", location.detailLocation)
            itemView.textTicketCount.text =
                String.format("Số khách : %s", location.number.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_choose_location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bindView(mLocations[position])
    }

    override fun getItemCount(): Int = mLocations.size
}