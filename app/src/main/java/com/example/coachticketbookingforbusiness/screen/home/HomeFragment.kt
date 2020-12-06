package com.example.coachticketbookingforbusiness.screen.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.screen.routes.RoutesFragment
import com.example.coachticketbookingforbusiness.utils.SharePreferenceUtils
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun initView() {
        toolbar.title = getString(R.string.title_home)
    }

    override fun initData(bundle: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun initObserver() {
    }

    override fun initListener() {
        containerRoutes.setOnClickListener(this)
        containerProfile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            containerRoutes -> {
//                SharePreferenceUtils.getLocalUserInformation(context)?.let {
                    val routeFragment = RoutesFragment.newInstance("0900888337")
                    pushFragment(routeFragment, withAnimation = true, tag = "Route")
//                }
            }
        }
    }

}