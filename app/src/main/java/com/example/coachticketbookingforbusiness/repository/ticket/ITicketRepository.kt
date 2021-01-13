package com.example.coachticketbookingforbusiness.repository.ticket


import com.example.coachticketbookingforbusiness.model.TicketDetail
import com.example.coachticketbookingforbusiness.model.TicketLocalModel
import io.reactivex.rxjava3.core.Single

interface ITicketRepository {
    fun createTicket(ticket: TicketLocalModel): Single<String>
    fun getMyTickets(id : Int, date : String): Single<List<TicketDetail>>
    fun getTicketDetail(id: Int): Single<TicketDetail>
    fun checkTicket(id: Int, date: String): Single<TicketDetail>
}