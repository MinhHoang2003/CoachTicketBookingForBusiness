package com.example.coachticketbookingforbusiness.screen.ticket

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.Ticket
import com.example.coachticketbookingforbusiness.model.TicketDetail
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.ticket.ITicketRepository
import com.example.coachticketbookingforbusiness.repository.ticket.TicketRepository


class TicketViewModel : BaseViewModel() {

    private val mTicketRepository: ITicketRepository by lazy {
        TicketRepository(RetrofitClient.getAPIService())
    }

    val myTicketsLiveData = MutableLiveData<List<TicketDetail>>(arrayListOf())

    fun getMyTickets(id: Int, date: String) {
        mTicketRepository.getMyTickets(id, date)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { tickets, err ->
                if (err == null) {
                    myTicketsLiveData.value = tickets
                }
            }
            .addToCompositeDisposable(disposable)
    }

}