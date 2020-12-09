package com.example.coachticketbookingforbusiness.screen.ticket

import android.os.Bundle
import android.widget.Toast
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.LocationPagerAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.screen.qr_scan.QRScanFragment
import es.dmoral.toasty.Toasty
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
        toolbar.setNavigationOnClickListener { popBackStack() }
        fab.setOnClickListener {
            val qrScanFragment = QRScanFragment.newInstance()
            qrScanFragment.setOnReceiveTicketId {
                context?.let { it1 -> Toasty.success(it1, it, Toast.LENGTH_LONG, true).show() }
            }
            pushFragment(qrScanFragment, withAnimation = false)
        }
    }


}