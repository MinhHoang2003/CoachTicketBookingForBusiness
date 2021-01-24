package com.example.coachticketbookingforbusiness.screen.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.dialog.DialogShowTicket
import com.example.coachticketbookingforbusiness.screen.authentication.login.LoginFragment
import com.example.coachticketbookingforbusiness.screen.profile.ProfileFragment
import com.example.coachticketbookingforbusiness.screen.qr_scan.QRScanFragment
import com.example.coachticketbookingforbusiness.screen.routes.RoutesFragment
import com.example.coachticketbookingforbusiness.utils.SharePreferenceUtils
import com.example.coachticketbookingforbusiness.utils.Utils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.home_fragment.*
import java.lang.Exception
import java.util.*

class HomeFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mHomeViewModel: HomeViewModel
    private var mDialogShowTicket : DialogShowTicket? = null
    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun initView() {
    }

    override fun initViewModel() {
        mHomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        context?.let {
            val user = SharePreferenceUtils.getLocalUserInformation(it)
            user?.apply {
                textUserName.text = name
            }
        }
    }

    override fun observerForever() {
        //Nothing
    }

    override fun observerOnce() {
        mHomeViewModel.ticket.observe(this, {
            mDialogShowTicket = DialogShowTicket(context!!)
            mDialogShowTicket?.showDialog(it)
        })
    }

    override fun initListener() {
        containerRoutes.setOnClickListener(this)
        containerProfile.setOnClickListener(this)
        containerQRScan?.setOnClickListener(this)
        imgLogOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            containerRoutes -> {
                SharePreferenceUtils.getLocalUserInformation(context)?.let {
                    val routeFragment = RoutesFragment.newInstance(it.phoneNumber)
                    pushFragment(routeFragment, withAnimation = true, tag = "Route")
                }
            }

            containerQRScan -> {
                val qrScanFragment = QRScanFragment.newInstance()
                qrScanFragment.setOnReceiveTicketId {
                    try {
                        val ticketId = it.toInt()
                        mHomeViewModel.checkTicket(
                            ticketId,
                            Utils.getServerDateFormat(Utils.getCurrentTime())
                        )
                    } catch (e: NumberFormatException) {

                    }
                }
                pushFragment(qrScanFragment, withAnimation = false)
            }

            imgLogOut -> {
                SharePreferenceUtils.logout(context)
                val loginFragment = LoginFragment.newInstance()
                pushFragment(loginFragment)
            }

            containerProfile -> {
                val profile = ProfileFragment.newInstance()
                pushFragment(profile)
            }

        }
    }

}