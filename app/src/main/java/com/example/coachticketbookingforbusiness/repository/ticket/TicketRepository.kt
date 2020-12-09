package com.example.coachticketbookingforbusiness.repository.ticket


import com.example.coachticketbookingforbusiness.model.TicketDetail
import com.example.coachticketbookingforbusiness.model.TicketLocalModel
import com.example.coachticketbookingforbusiness.networking.APIService
import io.reactivex.rxjava3.core.Single

class TicketRepository(private val apiService: APIService) : ITicketRepository {
    override fun createTicket(ticket: TicketLocalModel): Single<String> =
        apiService.createTicket(ticket)

    override fun getMyTickets(id: Int, date: String): Single<List<TicketDetail>> =
        apiService.getTicketsForRoute(id, date)

    override fun getTicketDetail(id: Int): Single<TicketDetail> = apiService.getTicketDetail(id)

}