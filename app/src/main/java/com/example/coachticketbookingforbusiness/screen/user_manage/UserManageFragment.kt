package com.example.coachticketbookingforbusiness.screen.user_manage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.UserAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.base.view.gone
import com.example.coachticketbookingforbusiness.base.view.visible
import com.example.coachticketbookingforbusiness.model.UserRole
import com.example.coachticketbookingforbusiness.screen.user_detail_manage.UserDetailManageFragment
import kotlinx.android.synthetic.main.user_fragment.*

class UserManageFragment : BaseFragment() {

    companion object {
        const val MODE_DRIVER = "DRIVER"
        const val MODE_USER = "USER"
        private const val MODE = "MODE"
        fun newInstance(mode: String) = UserManageFragment().apply {
            val bundle = Bundle().also {
                it.putString(MODE, mode)
            }
            arguments = bundle
        }
    }

    private lateinit var mUserManageViewModel: UserManageViewModel
    private val mUserAdapter = UserAdapter()
    private var mCurrentMode: String = MODE_DRIVER
    override fun getLayoutId(): Int = R.layout.user_fragment

    override fun initView() {
        recyclerUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mUserAdapter
        }
    }

    override fun initViewModel() {
        mUserManageViewModel = ViewModelProvider(this).get(UserManageViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        bundle?.let {
            mCurrentMode = it.getString(MODE, MODE_DRIVER)
        }

        mUserManageViewModel.getUsers(
            if (mCurrentMode == MODE_USER) {
                buttonAdd.text = "Thêm khách hàng"
                UserRole.USER.value
            } else {
                buttonAdd.text = "Thêm tài xế"
                UserRole.DRIVER.value
            }
        )
    }

    override fun observerForever() {
        mUserManageViewModel.mLoading.observe(this, {
            if (it) showLoading() else hideLoading()
        })

        mUserManageViewModel.userLiveData.observe(this, {
            if (it.isEmpty()) containerNoData.visible()
            else containerNoData.gone()
            mUserAdapter.setData(it)
        })
    }

    override fun observerOnce() {
    }

    override fun initListener() {
        mUserAdapter.onClickUserListener = {
            val userDetailFragment = UserDetailManageFragment.newInstance(it)
            pushFragment(userDetailFragment)
        }
        buttonAdd.setOnClickListener {
            val userDetailManageFragment = UserDetailManageFragment.newInstance()
            pushFragment(userDetailManageFragment)
        }
    }
}