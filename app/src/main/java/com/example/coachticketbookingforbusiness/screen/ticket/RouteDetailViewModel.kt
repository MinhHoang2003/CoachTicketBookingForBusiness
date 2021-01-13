package com.example.coachticketbookingforbusiness.screen.ticket

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbookingforbusiness.base.BaseViewModel
import com.example.coachticketbookingforbusiness.base.addToCompositeDisposable
import com.example.coachticketbookingforbusiness.base.applyScheduler
import com.example.coachticketbookingforbusiness.model.TicketDetail
import com.example.coachticketbookingforbusiness.networking.RetrofitClient
import com.example.coachticketbookingforbusiness.repository.ticket.TicketRepository

class RouteDetailViewModel : BaseViewModel() {
    private val mTicketRepository by lazy {
        TicketRepository.getInstance(RetrofitClient.getAPIService())
    }

    val ticket = MutableLiveData<TicketDetail>()

    fun checkTicket(id: Int, date: String) {
        mTicketRepository.checkTicket(id, date)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { ticket, err ->
                if (err == null) {
                    this.ticket.value = ticket
                }
            }.addToCompositeDisposable(disposable)
    }
}