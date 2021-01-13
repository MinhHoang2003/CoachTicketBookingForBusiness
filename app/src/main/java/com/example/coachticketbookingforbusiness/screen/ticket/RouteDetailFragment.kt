package com.example.coachticketbookingforbusiness.screen.ticket

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.LocationPagerAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.dialog.DialogShowTicket
import com.example.coachticketbookingforbusiness.screen.qr_scan.QRScanFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_route_detail.*

class RouteDetailFragment : BaseFragment() {

    companion object {
        fun newInstance(): RouteDetailFragment = RouteDetailFragment()
    }

    private lateinit var mRouteDetailViewModel: RouteDetailViewModel
    private var mDialogShowTicket : DialogShowTicket? = null

    override fun getLayoutId(): Int = R.layout.fragment_route_detail

    override fun initView() {
        val pagerAdapter = LocationPagerAdapter(1, "2020-11-15", childFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = getString(R.string.chooseLocationTitile)
    }

    override fun initViewModel() {
        mRouteDetailViewModel = ViewModelProvider(this).get(RouteDetailViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun observerForever() {

    }

    override fun observerOnce() {
        mRouteDetailViewModel.ticket.observe(this, {
            mDialogShowTicket = DialogShowTicket(context!!)
            mDialogShowTicket?.showDialog(it)
        })
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener { popBackStack() }
        fab.setOnClickListener {
            val qrScanFragment = QRScanFragment.newInstance()
            qrScanFragment.setOnReceiveTicketId {
                try {
                    val ticketId = it.toInt()
                    mRouteDetailViewModel.checkTicket(
                        ticketId,
                        "2020-11-15"
                    )
                } catch (e: NumberFormatException) {

                }
            }
            pushFragment(qrScanFragment, withAnimation = false)
        }
    }


}