package com.example.coachticketbookingforbusiness.screen.home_admin

import android.os.Bundle
import android.view.View
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.screen.authentication.login.LoginFragment
import com.example.coachticketbookingforbusiness.screen.coach_manage.CoachManageFragment
import com.example.coachticketbookingforbusiness.screen.route_manage.RouteManageFragment
import com.example.coachticketbookingforbusiness.screen.user_manage.UserPageFragment
import com.example.coachticketbookingforbusiness.utils.SharePreferenceUtils
import kotlinx.android.synthetic.main.fragment_home_for_admin.*
import kotlinx.android.synthetic.main.fragment_home_for_admin.containerProfile
import kotlinx.android.synthetic.main.fragment_home_for_admin.containerRoutes
import kotlinx.android.synthetic.main.fragment_home_for_admin.textUserName

class HomeForAdminFragment : BaseFragment(), View.OnClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = HomeForAdminFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_home_for_admin

    override fun initView() {
    }

    override fun initViewModel() {
        //Nothing
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
    }

    override fun observerOnce() {
        //Nothing
    }

    override fun initListener() {
        containerProfile.setOnClickListener(this)
        containerCoach.setOnClickListener(this)
        containerRoutes.setOnClickListener(this)
        imgLogOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            containerCoach -> {
                val coachManageFragment = CoachManageFragment()
                pushFragment(coachManageFragment)
            }

            containerProfile -> {
                val userFragment = UserPageFragment.newInstance()
                pushFragment(userFragment)
            }

            containerRoutes -> {
                val routeManageFragment = RouteManageFragment.newInstance()
                pushFragment(routeManageFragment)
            }

            imgLogOut -> {
                SharePreferenceUtils.logout(context)
                val loginFragment = LoginFragment.newInstance()
                pushFragment(loginFragment)
            }
        }
    }
}