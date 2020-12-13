package com.example.coachticketbookingforbusiness.screen

import android.os.Bundle
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseActivity
import com.example.coachticketbookingforbusiness.screen.home_admin.HomeForAdminFragment

class MainActivity : BaseActivity() {

    override fun getContainerFragmentView(): Int = R.id.fragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeForAdminFragment.newInstance()
        pushFragment(homeFragment, withAnimation = false, tag = "Home")

    }

    override fun onBackPressed() {
        popBackStack()
    }
}