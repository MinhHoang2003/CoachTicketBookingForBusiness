package com.example.coachticketbookingforbusiness.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.coachticketbookingforbusiness.screen.user_manage.UserManageFragment


class UserPageAdapter(
    fmn: FragmentManager
) : FragmentStatePagerAdapter(fmn, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UserManageFragment.newInstance(UserManageFragment.MODE_DRIVER)
            else -> UserManageFragment.newInstance(UserManageFragment.MODE_USER)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Tài xế"
            else -> "Khách hàng"
        }
    }
}