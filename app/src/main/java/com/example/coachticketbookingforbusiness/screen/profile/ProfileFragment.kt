package com.example.coachticketbookingforbusiness.screen.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.base.view.gone
import com.example.coachticketbookingforbusiness.base.view.visible
import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.model.UserRole
import com.example.coachticketbookingforbusiness.utils.Constants
import com.example.coachticketbookingforbusiness.utils.SharePreferenceUtils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var mProfileViewModel: ProfileViewModel
    override fun getLayoutId(): Int = R.layout.profile_fragment

    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = "Thay đổi thông tin cá nhân"
    }

    override fun initViewModel() {
        mProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        SharePreferenceUtils.getLocalUserInformation(context)?.let {
            mProfileViewModel.getProfile(it.phoneNumber)
        }
    }

    override fun observerForever() {
        mProfileViewModel.user.observe(this, {
            setProfileData(it)
        })
    }

    private fun setProfileData(user: User) {
        context?.apply { SharePreferenceUtils.saveUserData(this, user) }
        edtPhoneNumber.setText(user.phoneNumber)
        edtName.setText(user.name)
        edtEmail.setText(user.email)
        edtAddress.setText(user.address)
        showPasswordChangeMode(true)
    }

    private fun showPasswordChangeMode(isShow: Boolean) {
        if (isShow) {
            textChangePassword.text = "Ẩn thay đổi mật khẩu"
            passwordContainer.visible()
        } else {
            textChangePassword.text = "Hiện thay đổi mật khẩu"
            passwordContainer.gone()
        }
    }

    private fun getUserDetail(): User {
        return User(
            edtPhoneNumber.text.toString(),
            edtName.text.toString(),
            Constants.EMPTY_STRING,
            edtEmail.text.toString(),
            edtPassword.text.toString(),
            edtAddress.text.toString(),
            UserRole.DRIVER.value
        )
    }

    override fun observerOnce() {
        mProfileViewModel.isUpdateComplete.observe(this, {
            if (it) {
                context?.apply {
                    Toasty.success(
                        this,
                        "Cập nhật thông tin thành công!!!",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }
                val phone = edtPhoneNumber.text.toString()
                mProfileViewModel.getProfile(phone)
                showPasswordChangeMode(false)
            }
        })
    }

    private fun checkPassword(): String? {
        if (passwordContainer.visibility == View.GONE) return null
        if (edtPassword.text.isNullOrBlank()) return "Mật khẩu không được để trống"
        if (edtConfirmPassword.text.isNullOrBlank()) return "Xác nhận Mật khẩu không được để trống"
        if (edtPassword.text.toString() != edtConfirmPassword.text.toString()) return "Mật khẩu và xác nhận mật khẩu không trùng khớp"
        return Constants.EMPTY_STRING
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener {
            popBackStack()
        }
        btnApply.setOnClickListener {
            SharePreferenceUtils.getLocalUserInformation(context)?.let {
                when {
                    checkPassword() == null -> {
                        mProfileViewModel.update(getUserDetail(), it.phoneNumber)
                    }
                    checkPassword() == Constants.EMPTY_STRING -> {
                        mProfileViewModel.update(
                            getUserDetail(),
                            edtCurrentPassword.text.toString(),
                            it.phoneNumber
                        )
                    }
                    else -> {
                        context?.apply {
                            Toasty.error(this, checkPassword().toString(), Toast.LENGTH_SHORT, true)
                                .show()
                        }
                    }
                }
            }
        }

        textChangePassword.setOnClickListener {
            if (passwordContainer.visibility == View.VISIBLE) {
                showPasswordChangeMode(false)
            } else showPasswordChangeMode(true)
        }
    }
}