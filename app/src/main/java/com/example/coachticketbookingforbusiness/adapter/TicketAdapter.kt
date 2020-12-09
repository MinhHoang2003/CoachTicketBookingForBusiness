package com.example.coachticketbookingforbusiness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.model.TicketDetail
import kotlinx.android.synthetic.main.layout_ticket_item.view.*

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.MyTicketHolder>() {

    private val mTickets = mutableListOf<TicketDetail>()
    private var mOnTicketClickListener: ((id: Int) -> Unit)? = null

    fun setData(tickets: List<TicketDetail>) {
        mTickets.clear()
        mTickets.addAll(tickets)
        notifyDataSetChanged()
    }

    fun setOnTicketClickListener(listener: (id: Int) -> Unit) {
        mOnTicketClickListener = listener
    }

    inner class MyTicketHolder(
        itemView: View,
        private val onTicketClickListener: ((id: Int) -> Unit)? = null
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val textTicketPickLocation = itemView.textPickLocation
        private val textDestination = itemView.textDestination
        private val textPhoneNumber = itemView.textPhoneNumber
        private val textTicketPosition = itemView.textTicketPositionCode

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(ticket: TicketDetail) {
            textTicketPickLocation.text =
                String.format("Điểm đón : %s", ticket.pickLocation.detailLocation)
            textDestination.text = String.format("Điểm trả : %s", ticket.destination.detailLocation)
            textPhoneNumber.text = String.format("SDT : %s", ticket.useId)
            val position =
                ticket.positionCode.toString()
                    .substring(1, ticket.positionCode.toString().length - 1)

            textTicketPosition.text = String.format("Chỗ ngồi : %s", position)
        }

        override fun onClick(v: View?) {
            onTicketClickListener?.invoke(mTickets[adapterPosition].id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTicketHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_ticket_item, parent, false)
        return MyTicketHolder(view, mOnTicketClickListener)
    }

    override fun onBindViewHolder(holder: MyTicketHolder, position: Int) {
        holder.bindView(mTickets[position])
    }

    override fun getItemCount(): Int = mTickets.size

}