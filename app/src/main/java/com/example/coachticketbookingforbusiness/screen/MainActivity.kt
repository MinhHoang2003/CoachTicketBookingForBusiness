package com.example.coachticketbookingforbusiness.screen

import android.os.Bundle
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseActivity
import com.example.coachticketbookingforbusiness.screen.splash.SplashFragment

class MainActivity : BaseActivity() {

    override fun getContainerFragmentView(): Int = R.id.fragmentContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val splashFragment = SplashFragment.newInstance()
        pushFragment(splashFragment, withAnimation = false, tag = "Login")

    }

    override fun onBackPressed() {
        popBackStack()
    }
}