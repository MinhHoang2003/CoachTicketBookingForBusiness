package com.example.coachticketbookingforbusiness.screen.authentication.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.model.UserRole
import com.example.coachticketbookingforbusiness.screen.authentication.login.LoginFragment
import com.example.coachticketbookingforbusiness.screen.home.HomeFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = RegisterFragment()
        const val ERROR_CODE_PASSWORD_NOT_SAME = 1
        const val VALIDATE_FIELDS = 0
    }

    private var mRegisterViewModel: RegisterViewModel? = null
    override fun getLayoutId(): Int = R.layout.register_fragment

    override fun initView() {
    }

    override fun initViewModel() {
        context?.let {
            mRegisterViewModel = ViewModelProvider(
                this,
                RegisterViewModelFactory(it)
            ).get(RegisterViewModel::class.java)
        }
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun observerForever() {
        mRegisterViewModel?.registerResultLiveData?.observe(this, {
            context?.let { context ->
                if (it) {
                    Toasty.success(
                        context,
                        getString(R.string.message_success_register),
                        Toast.LENGTH_LONG,
                        true
                    ).show()

                    val home = HomeFragment.newInstance()
                    replaceFragment(home)
                }
            }
        })

        mRegisterViewModel?.mLoading?.observe(this, {
            if (it) showLoading() else hideLoading()
        })
    }

    override fun observerOnce() {
    }

    override fun initListener() {
        btnRegister.setOnClickListener(this)
        textSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnRegister -> {
                getUserData { user, err ->
                    if (err == VALIDATE_FIELDS) {
                        mRegisterViewModel?.register(user!!)
                    }
                }
            }

            textSignIn -> {
                val loginFragment = LoginFragment.newInstance()
                replaceFragment(loginFragment, withAnimation = true, tag = "Login")
            }
        }
    }

    private fun getUserData(result: (User?, Int) -> Unit) {
        val phoneNumber = edtPhoneNumber.text.toString()
        val name = edtName.text.toString()
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        val confirmPassword = edtConfirmPassword.text.toString()
        val address = edtAddress.text.toString()
        if (password == confirmPassword) {
            result.invoke(
                User(
                    phoneNumber,
                    name,
                    "",
                    email,
                    password,
                    address,
                    UserRole.DRIVER.value
                ), VALIDATE_FIELDS
            )
        }
        result.invoke(null, ERROR_CODE_PASSWORD_NOT_SAME)
    }
}