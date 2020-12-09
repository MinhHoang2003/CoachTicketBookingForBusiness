package com.example.coachticketbookingforbusiness.screen.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.LocationPagerAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_route_detail.*

class RouteDetailFragment : BaseFragment() {

    companion object {
        fun newInstance(): RouteDetailFragment = RouteDetailFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_route_detail

    override fun initView() {
        val pagerAdapter = LocationPagerAdapter(1, "2020-11-15", childFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = getString(R.string.chooseLocationTitile)
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun initObserver() {

    }

    override fun initListener() {

    }


}