package com.example.coachticketbookingforbusiness.screen.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.model.UserRole
import com.example.coachticketbookingforbusiness.screen.authentication.login.LoginFragment
import com.example.coachticketbookingforbusiness.screen.home.HomeFragment
import com.example.coachticketbookingforbusiness.screen.home_admin.HomeForAdminFragment
import com.example.coachticketbookingforbusiness.utils.SharePreferenceUtils
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_splash

    override fun initView() {
        val topAnimation = AnimationUtils.loadAnimation(context, R.anim.top_down)
        val downAnimation = AnimationUtils.loadAnimation(context, R.anim.down_top)

        imgSplash.animation = topAnimation
        textView2.animation = downAnimation
    }

    override fun initViewModel() {
    }

    override fun initData(bundle: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            loadUserData()
        }, 500)
    }

    private fun loadUserData() {
        if (context == null) {
            replaceFragment(LoginFragment.newInstance(), withAnimation = true)
            return
        }
        context?.let {
            val user = SharePreferenceUtils.getLocalUserInformation(it)
            val fragment = when {
                user == null -> {
                    LoginFragment.newInstance()
                }
                user.role == UserRole.DRIVER.value -> {
                    HomeFragment.newInstance()
                }
                else -> {
                    HomeForAdminFragment.newInstance()
                }
            }
            replaceFragment(fragment, withAnimation = true)
        }
    }

    override fun observerForever() {
    }

    override fun observerOnce() {
    }

    override fun initListener() {
    }
}