package com.example.coachticketbookingforbusiness.screen.user_manage

import android.os.Bundle
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.UserPageAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_page.*


class UserPageFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = UserPageFragment()
    }


    override fun getLayoutId(): Int = R.layout.fragment_user_page

    override fun initView() {
        val pagerAdapter = UserPageAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = getString(R.string.title_user_list)
    }

    override fun initViewModel() {
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun observerForever() {
    }

    override fun observerOnce() {
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener {
            popBackStack()
        }
    }

}