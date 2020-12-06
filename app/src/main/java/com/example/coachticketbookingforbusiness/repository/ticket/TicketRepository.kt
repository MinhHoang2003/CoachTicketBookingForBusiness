package com.example.coachticketbookingforbusiness.repository.ticket


import com.example.coachticketbookingforbusiness.model.Ticket
import com.example.coachticketbookingforbusiness.model.TicketDetail
import com.example.coachticketbookingforbusiness.model.TicketLocalModel
import com.example.coachticketbookingforbusiness.networking.APIService
import com.example.coachticketbookingforbusiness.repository.ticket.ITicketRepository
import io.reactivex.rxjava3.core.Single

class TicketRepository(private val apiService: APIService) : ITicketRepository {
    override fun createTicket(ticket: TicketLocalModel): Single<String> =
        apiService.createTicket(ticket)

    override fun getMyTickets(phoneNumber: String): Single<List<Ticket>> =
        apiService.getMyTickets(phoneNumber)

    override fun getTicketDetail(id: Int): Single<TicketDetail> = apiService.getTicketDetail(id)

}