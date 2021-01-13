package com.example.coachticketbookingforbusiness.repository.ticket


import com.example.coachticketbookingforbusiness.model.TicketDetail
import com.example.coachticketbookingforbusiness.model.TicketLocalModel
import com.example.coachticketbookingforbusiness.networking.APIService
import com.example.coachticketbookingforbusiness.repository.route.IRouteRepository
import com.example.coachticketbookingforbusiness.repository.route.RouteRepository
import io.reactivex.rxjava3.core.Single

class TicketRepository(private val apiService: APIService) : ITicketRepository {

    companion object {
        var INSTANCE: ITicketRepository? = null

        fun getInstance(apiService: APIService): ITicketRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TicketRepository(apiService).also { INSTANCE = it }
            }

    }

    override fun createTicket(ticket: TicketLocalModel): Single<String> =
        apiService.createTicket(ticket)

    override fun getMyTickets(id: Int, date: String): Single<List<TicketDetail>> =
        apiService.getTicketsForRoute(id, date)

    override fun getTicketDetail(id: Int): Single<TicketDetail> = apiService.getTicketDetail(id)
    override fun checkTicket(id: Int, date: String): Single<TicketDetail> =
        apiService.checkTicket(id, date)

}