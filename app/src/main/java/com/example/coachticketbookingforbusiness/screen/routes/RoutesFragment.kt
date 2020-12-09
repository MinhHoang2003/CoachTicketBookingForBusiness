package com.example.coachticketbookingforbusiness.screen.routes

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.RouteAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.screen.ticket.RouteDetailFragment
import com.example.coachticketbookingforbusiness.screen.ticket.TicketFragment
import kotlinx.android.synthetic.main.fragment_routes.*

class RoutesFragment : BaseFragment() {

    companion object {
        const val KEY_SEARCH_QUERY = "search_query"

        fun newInstance(phoneNumber: String) =
            RoutesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_SEARCH_QUERY, phoneNumber)
                }
            }
    }

    lateinit var mRoutesViewModel: RoutesViewModel
    private val routesAdapter = RouteAdapter()


    override fun initData(bundle: Bundle?) {
        mRoutesViewModel = ViewModelProvider(this).get(RoutesViewModel::class.java)
        bundle?.apply {
            val search = getString(KEY_SEARCH_QUERY)
            search?.apply {
//                textDate.text = date
                mRoutesViewModel.searchRoutes(search, "2020-11-15")
            }
        }
    }

    override fun initView() {
        recyclerRoutes.adapter = routesAdapter
        recyclerRoutes.layoutManager = LinearLayoutManager(context)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = getString(R.string.title_route)
    }

    override fun initListener() {
        routesAdapter.onItemClick = { selectedRoute ->
            val myTicketFragment = RouteDetailFragment.newInstance()
            pushFragment(myTicketFragment)
        }
        toolbar.setNavigationOnClickListener {
            popBackStack()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_routes


    override fun initObserver() {
        mRoutesViewModel.mLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLoading()
            } else hideLoading()
        })

        mRoutesViewModel.mError.observe(this, {
            // TODO show error dialog.
        })

        mRoutesViewModel.routesLiveData.observe(this, {
            routesAdapter.setData(it)
        })
    }
}