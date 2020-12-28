package com.example.coachticketbookingforbusiness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.model.Route
import kotlinx.android.synthetic.main.layout_route_items.view.*

class RouteAdapter : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    private val routes = mutableListOf<Route>()
    var onItemClick: ((route: Route) -> Unit)? = null

    fun setData(routes: List<Route>) {
        this.routes.clear()
        this.routes.addAll(routes)
        notifyDataSetChanged()
    }

    inner class RouteViewHolder(
        itemView: View,
        private val onItemClick: ((route: Route) -> Unit)? = null
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val textStartTime: TextView = itemView.textStartTime
        private val textStartLocation: TextView = itemView.textStartLocation
        private val textEndTime: TextView = itemView.textEndTime
        private val textEndLocation: TextView = itemView.textEndLocation
        private val textPrice: TextView = itemView.textPrice
        private val textPositionNumber: TextView = itemView.textPositionNumber
        private val textRemainingPositionNumber: TextView = itemView.textRemainingPositionNumber

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(route: Route) {
            textStartTime.text = getDataWithCheck(route.startTime)
            textStartLocation.text = getDataWithCheck(route.startAddress)
            textEndTime.text = getDataWithCheck(route.endTime)
            textEndLocation.text = getDataWithCheck(route.endAddress)
            textPrice.text = String.format("%s d", route.price.toString())
            textPositionNumber.text =
                String.format("Giường nằm %s chỗ", route.numberPosition.toString())
            textRemainingPositionNumber.text =
                String.format("(Còn %s chỗ)", route.numberPosition - route.remain)
        }

        override fun onClick(v: View?) {
            onItemClick?.invoke(routes[adapterPosition])
        }
    }

    private fun getDataWithCheck(value: String?): String {
        return if (value == null || value.isBlank()) "Chưa có thông tin" else value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_route_items, parent, false)
        return RouteViewHolder(itemView, onItemClick)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bindView(routes[position])
    }

    override fun getItemCount(): Int = routes.size

}