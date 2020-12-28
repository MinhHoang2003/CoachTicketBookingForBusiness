package com.example.coachticketbookingforbusiness.screen.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.model.UserRole
import com.example.coachticketbookingforbusiness.screen.authentication.register.RegisterFragment
import com.example.coachticketbookingforbusiness.screen.home.HomeFragment
import com.example.coachticketbookingforbusiness.screen.home_admin.HomeForAdminFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var mLoginViewModel: LoginViewModel
    override fun getLayoutId(): Int = R.layout.login_fragment

    override fun initView() {
        //Nothing
    }

    override fun initViewModel() {
        context?.let {
            mLoginViewModel = ViewModelProvider(
                this,
                LoginViewModelFactory(it)
            ).get(LoginViewModel::class.java)
        }
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun observerForever() {
        mLoginViewModel.mLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLoading()
            } else hideLoading()
        })
        mLoginViewModel.loginResultLiveData.observe(this, { result ->
            if (result != null) {
                context?.let { context ->
                    Toasty.success(
                        context,
                        "Đăng nhập thành công",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }
                val home = if (result == UserRole.DRIVER) {
                    HomeFragment.newInstance()
                } else HomeForAdminFragment.newInstance()

                replaceFragment(home, withAnimation = false)
            }
        })
    }

    override fun observerOnce() {
        //Nothing
    }

    override fun initListener() {
        btnLogin.setOnClickListener(this)
        textSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnLogin -> {
                val phoneNumber: String = edtPhoneNumber.text.toString()
                val password: String = edtPassword.text.toString()
                mLoginViewModel.login(phoneNumber, password)
            }

            textSignUp -> {
                val registerFragment = RegisterFragment.newInstance()
                replaceFragment(registerFragment, withAnimation = true, tag = "Register")
            }
        }
    }

}