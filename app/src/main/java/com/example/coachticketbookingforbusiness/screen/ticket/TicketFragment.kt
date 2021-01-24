package com.example.coachticketbookingforbusiness.screen.ticket

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.LocationAdapter
import com.example.coachticketbookingforbusiness.adapter.TicketAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.screen.location.LocationFragment
import com.example.coachticketbookingforbusiness.utils.Utils
import kotlinx.android.synthetic.main.my_ticket_fragment.*

class TicketFragment : BaseFragment() {

    companion object {

        private const val KEY_ROUTE_ID = "ID"
        private const val KEY_DATE = "DATE"

        fun newInstance(id: Int, date: String) = TicketFragment().apply {
            val bundle = Bundle()
            bundle.putInt(KEY_ROUTE_ID, id)
            bundle.putString(KEY_DATE, date)
            arguments = bundle
        }
    }

    private lateinit var mMyTicketViewModel: TicketViewModel
    private val mMyTicketAdapter = TicketAdapter()

    override fun getLayoutId(): Int = R.layout.my_ticket_fragment

    override fun initView() {
        recyclerTickets?.apply {
            adapter = mMyTicketAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initViewModel() {
        mMyTicketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        bundle?.let {
            mMyTicketViewModel.getMyTickets(
                1, Utils.getServerDateFormat(
                    Utils.getCurrentTime()
                )
            )
        }
    }

    override fun observerForever() {
        mMyTicketViewModel.myTicketsLiveData.observe(this, {
            mMyTicketAdapter.setData(it)
        })

        mMyTicketViewModel.mLoading.observe(this, {
            if (it) showLoading() else hideLoading()
        })
    }

    override fun observerOnce() {
    }

    override fun initListener() {
        mMyTicketAdapter.setOnTicketClickListener {
            val locationFragment = LocationFragment.newInstance(1, "2020-11-15")
            pushFragment(locationFragment, withAnimation = false)
        }
    }
}