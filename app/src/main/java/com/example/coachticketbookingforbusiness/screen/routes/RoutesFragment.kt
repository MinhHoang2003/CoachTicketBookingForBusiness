package com.example.coachticketbookingforbusiness.screen.routes

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.RouteAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.model.RouteSearchPattern
import kotlinx.android.synthetic.main.fragment_routes.*

class RoutesFragment : BaseFragment() {

    companion object {
        const val KEY_SEARCH_QUERY = "search_query"

        fun newInstance(routeSearchPattern: RouteSearchPattern) =
            RoutesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_SEARCH_QUERY, routeSearchPattern)
                }
            }
    }

    lateinit var mRoutesViewModel: RoutesViewModel
    private var search: RouteSearchPattern? = null
    private val routesAdapter = RouteAdapter()


    override fun initData(bundle: Bundle?) {
        mRoutesViewModel = ViewModelProvider(this).get(RoutesViewModel::class.java)
        bundle?.apply {
            search = getParcelable(KEY_SEARCH_QUERY)
            search?.apply {
                textRouteTitle.text = String.format("%s --> %s", pickLocation, destination)
                textDate.text = date
                mRoutesViewModel.searchRoutes(pickLocation, destination, date)
            }
        }
    }

    override fun initView() {
        recyclerRoutes.adapter = routesAdapter
        recyclerRoutes.layoutManager = LinearLayoutManager(context)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
    }

    override fun initListener() {
        routesAdapter.onItemClick = { selectedRoute ->
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