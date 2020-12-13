package com.example.coachticketbookingforbusiness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.model.Coach
import kotlinx.android.synthetic.main.layout_coach_item.view.*

class CoachAdapter : RecyclerView.Adapter<CoachAdapter.CoachViewHolder>() {

    private val mCoach = mutableListOf<Coach>()

    var onClickCoachListener: ((id: String) -> Unit)? = null

    fun setData(coach: List<Coach>) {
        mCoach.clear()
        mCoach.addAll(coach)
        notifyDataSetChanged()
    }

    inner class CoachViewHolder(
        itemView: View,
        private val listener: ((id: String) -> Unit)? = null
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(coach: Coach) {
            itemView.coachId.text = String.format("Biến số xe: %s", coach.id)
            itemView.textCarBrand.text = String.format("Hãng xe: %s", coach.carBrand)
            itemView.textPositionNumber.text =
                String.format("Số chỗ ngồi : %s", coach.numberPosition.toString())
            itemView.textFloorNumber.text =
                String.format("Số tầng : %s", coach.numberFloor.toString())
            itemView.textUserId.text = String.format("Số điện thoại tài xê: %s", coach.driverId)
        }

        override fun onClick(v: View?) {
            listener?.invoke(mCoach[adapterPosition].id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_coach_item, parent, false)
        return CoachViewHolder(view, onClickCoachListener)
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        holder.bindView(mCoach[position])
    }

    override fun getItemCount(): Int = mCoach.size

}