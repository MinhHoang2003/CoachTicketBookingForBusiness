package com.example.coachticketbookingforbusiness.screen.home_admin

import android.os.Bundle
import android.view.View
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.screen.coach_manage.CoachManageFragment
import com.example.coachticketbookingforbusiness.screen.user_manage.UserManageFragment
import com.example.coachticketbookingforbusiness.screen.user_manage.UserPageFragment
import kotlinx.android.synthetic.main.fragment_home_for_admin.*

class HomeForAdminFragment : BaseFragment(), View.OnClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = HomeForAdminFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_home_for_admin

    override fun initView() {
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun initObserver() {
    }

    override fun initListener() {
        containerProfile.setOnClickListener(this)
        containerCoach.setOnClickListener(this)
        containerRoutes.setOnClickListener(this)
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
        }
    }
}