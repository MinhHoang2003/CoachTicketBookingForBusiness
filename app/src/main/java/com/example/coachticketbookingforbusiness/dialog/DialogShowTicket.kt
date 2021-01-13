package com.example.coachticketbookingforbusiness.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.model.Ticket
import com.example.coachticketbookingforbusiness.model.TicketDetail
import kotlinx.android.synthetic.main.layout_dialog_show_ticket.*
import java.lang.Exception

class DialogShowTicket (context : Context): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_dialog_show_ticket)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showDialog(ticket : TicketDetail) {
        try {
            if (!isShowing) {
                show()
                showTicket(ticket)
            }
        } catch (e: Exception) {

        }
    }

    private fun showTicket(ticket: TicketDetail) {
        textPhoneNumber.text = String.format("Số điện thoại khách : %s", ticket.useId)
        textPosition.text = String.format("Mã chỗ ngồi : %s", ticket.positionCode.toString())
        textEndLocation.text =
            String.format("Điểm trả khách : %s", ticket.destination.detailLocation)
    }

    fun hideDialog() = this.dismiss()
}