package com.example.coachticketbookingforbusiness.screen.route_manage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.RouteAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.base.view.gone
import com.example.coachticketbookingforbusiness.base.view.visible
import com.example.coachticketbookingforbusiness.screen.route_detail_manage.RouteDetailManageFragment
import com.example.coachticketbookingforbusiness.screen.ticket.RouteDetailFragment
import kotlinx.android.synthetic.main.route_manage_fragment.*

class RouteManageFragment : BaseFragment() {

    companion object {
        fun newInstance() = RouteManageFragment()
    }

    private lateinit var mRouteManageViewModel: RouteManageViewModel
    private val mRouteAdapter = RouteAdapter()
    override fun getLayoutId(): Int = R.layout.route_manage_fragment

    override fun initView() {
        toolbar.title = getString(R.string.title_route_manage)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        recyclerRoutes.apply {
            adapter = mRouteAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initViewModel() {
        mRouteManageViewModel = ViewModelProvider(this).get(RouteManageViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        mRouteManageViewModel.getRoutes()
    }

    override fun observerForever() {
        mRouteManageViewModel.mLoading.observe(this, {
            if (it) showLoading() else hideLoading()
        })

        mRouteManageViewModel.routeLiveData.observe(this, {
            if (it.isEmpty()) containerNoData.visible()
            else containerNoData.gone()
            mRouteAdapter.setData(it)
        })
    }

    override fun observerOnce() {
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener { popBackStack() }
        mRouteAdapter.onItemClick = {
            val routeDetailFragment = RouteDetailManageFragment.newInstance(it.id)
            pushFragment(routeDetailFragment)
        }

        fabAddRoute.setOnClickListener {
            val routeDetailFragment = RouteDetailManageFragment.newInstance(-1)
            pushFragment(routeDetailFragment)
        }
    }

}