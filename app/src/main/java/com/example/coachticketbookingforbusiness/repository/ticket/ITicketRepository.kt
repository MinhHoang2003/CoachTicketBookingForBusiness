package com.example.coachticketbookingforbusiness.repository.ticket


import com.example.coachticketbookingforbusiness.model.Ticket
import com.example.coachticketbookingforbusiness.model.TicketDetail
import com.example.coachticketbookingforbusiness.model.TicketLocalModel
import io.reactivex.rxjava3.core.Single

interface ITicketRepository {
    fun createTicket(ticket: TicketLocalModel): Single<String>
    fun getMyTickets(phoneNumber: String): Single<List<Ticket>>
    fun getTicketDetail(id: Int): Single<TicketDetail>
}