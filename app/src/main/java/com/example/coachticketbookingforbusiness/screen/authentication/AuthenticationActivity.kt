package com.example.coachticketbookingforbusiness.screen.authentication

import android.os.Bundle
import com.example.coachticketbookingforbusiness.base.BaseActivity
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.screen.authentication.login.LoginFragment

class AuthenticationActivity : BaseActivity() {

    override fun getContainerFragmentView(): Int = R.id.authenticationFragmentContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        initView()
    }

    private fun initView() {
        val loginFragment = LoginFragment.newInstance()
        pushFragment(loginFragment, withAnimation = false, tag = "Login")
    }

    override fun onBackPressed() {
        popBackStack()
    }
}