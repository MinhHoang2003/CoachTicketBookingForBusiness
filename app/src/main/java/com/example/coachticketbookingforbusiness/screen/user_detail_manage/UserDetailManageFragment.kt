package com.example.coachticketbookingforbusiness.screen.user_detail_manage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.model.User
import com.example.coachticketbookingforbusiness.model.UserRole
import com.example.coachticketbookingforbusiness.utils.Constants
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.user_detail_manage_fragment.*
import kotlinx.android.synthetic.main.user_detail_manage_fragment.edtPhoneNumber
import kotlinx.android.synthetic.main.user_detail_manage_fragment.toolbar
import kotlin.jvm.Throws

class UserDetailManageFragment : BaseFragment() {

    companion object {
        private const val VALUE_SPINNER_USER = "Khách hàng"
        private const val VALUE_SPINNER_DRIVER = "Tài xế"
        private const val KEY_USER_ID = "ID"
        private const val MODE_ADD_NEW = "ADD_NEW"
        private const val MODE_EDIT = "EDIT"

        fun newInstance(id: String? = null) = UserDetailManageFragment().apply {
            id?.let {
                val bundle = Bundle().also {
                    it.putString(KEY_USER_ID, id)
                }
                arguments = bundle
            }
        }
    }

    private var mCurrentMode = MODE_ADD_NEW
    private var mCurrentId = Constants.EMPTY_STRING
    private lateinit var mUserDetailManageViewModel: UserDetailManageViewModel
    override fun getLayoutId(): Int = R.layout.user_detail_manage_fragment
    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
    }

    override fun initData(bundle: Bundle?) {
        mUserDetailManageViewModel =
            ViewModelProvider(this).get(UserDetailManageViewModel::class.java)
        bundle?.let {
            mCurrentId = it.getString(KEY_USER_ID, Constants.EMPTY_STRING)
            mCurrentMode = if (mCurrentId == Constants.EMPTY_STRING) MODE_ADD_NEW else {
                mUserDetailManageViewModel.getUser(mCurrentId)
                MODE_EDIT
            }
        }

        context?.let {
            val arrayAdapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                arrayListOf(
                    VALUE_SPINNER_DRIVER, VALUE_SPINNER_USER
                )
            )
            spinnerRole.adapter = arrayAdapter
        }

        if (mCurrentMode == MODE_EDIT) {
            toolbar.title = "Sửa thông tin người dùng"
            mUserDetailManageViewModel.getUser(mCurrentId)
        } else {
            toolbar.title = "Thêm mới người dùng"
        }

    }

    override fun initObserver() {

        mUserDetailManageViewModel.userLiveData.observe(this, {
            setData(it)
        })

        mUserDetailManageViewModel.updateResultLiveDate.observe(this, {
            if (it) {
                context?.apply {
                    Toasty.success(
                        this,
                        "Cập nhật thông tin thành công!!!",
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            }
        })

        mUserDetailManageViewModel.mLoading.observe(this, {
            if (it) showLoading()
            else hideLoading()
        })

        mUserDetailManageViewModel.mError.observe(this, {
            if (it == Constants.EMPTY_STRING) return@observe
            context?.apply {
                Toasty.error(
                    this,
                    it,
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }
        })

    }

    override fun initListener() {
        btnApply.setOnClickListener {
            if (mCurrentMode == MODE_ADD_NEW) mUserDetailManageViewModel.createUser(
                getUserInformation()
            )
            else mUserDetailManageViewModel.updateUser(getUserInformation(), mCurrentId)
        }

        toolbar.setNavigationOnClickListener { popBackStack() }

    }

    private fun setData(user: User?) {
        user?.apply {
            edtPhoneNumber.setText(user.phoneNumber)
            edtName.setText(user.name)
            edtEmail.setText(user.email)
            edtAddress.setText(user.address)
            edtPassword.setText(user.password)
            spinnerRole.setSelection(
                if (user.role == UserRole.USER.value) 1 else 0
            )
        }
    }

    @Throws(NumberFormatException::class)
    private fun getUserInformation(): User {
        val phoneNumber = edtPhoneNumber.text.toString()
        val name = edtName.text.toString()
        val email = edtEmail.text.toString()
        val pass = edtPassword.text.toString()
        val address = edtAddress.text.toString()
        val role =
            if (spinnerRole.selectedItemPosition == 0) UserRole.DRIVER.value else UserRole.USER.value
        return User(
            phoneNumber = phoneNumber,
            name = name,
            email = email,
            avatarUrl = Constants.EMPTY_STRING,
            password = pass,
            address = address,
            role = role
        )
    }

}