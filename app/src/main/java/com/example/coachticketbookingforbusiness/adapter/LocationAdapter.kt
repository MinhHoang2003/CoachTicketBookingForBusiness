package com.example.coachticketbookingforbusiness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.view.gone
import com.example.coachticketbookingforbusiness.base.view.invisible
import com.example.coachticketbookingforbusiness.base.view.visible
import com.example.coachticketbookingforbusiness.model.Location
import kotlinx.android.synthetic.main.layout_choose_location_item.view.*


class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private val mLocations = mutableListOf<Location>()
    var onClickLocation: ((id: Int) -> Unit)? = null

    var isModeManager: Boolean = false
    fun setData(data: List<Location>) {
        mLocations.clear()
        mLocations.addAll(data)
        notifyDataSetChanged()
    }

    inner class LocationViewHolder(itemView: View, private val listener: ((id: Int) -> Unit)?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(location: Location) {
            val format =
                if (location.stopStationType == 0 || location.stopStationType == 1) "Điểm đón: %s"
                else "Điểm trả : %s"

            itemView.textPosition.text = String.format(format, location.detailLocation)
            if (!isModeManager) {
                itemView.textTicketCount.visible()
                itemView.divier.visible()
                itemView.textTicketCount.text =
                    String.format("Số khách : %s", location.number.toString())
            } else {
                itemView.divier.gone()
                itemView.textTicketCount.gone()
            }
        }

        override fun onClick(v: View?) {
            listener?.invoke(mLocations[adapterPosition].id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_choose_location_item, parent, false)
        return LocationViewHolder(view, onClickLocation)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bindView(mLocations[position])
    }

    override fun getItemCount(): Int = mLocations.size
}