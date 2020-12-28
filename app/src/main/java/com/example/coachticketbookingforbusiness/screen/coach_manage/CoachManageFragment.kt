package com.example.coachticketbookingforbusiness.screen.coach_manage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.CoachAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.base.view.gone
import com.example.coachticketbookingforbusiness.base.view.visible
import com.example.coachticketbookingforbusiness.screen.coach_detail_manage.CoachDetailManageFragment
import kotlinx.android.synthetic.main.coach_manage_fragment.*

class CoachManageFragment : BaseFragment() {

    companion object {
        fun newInstance() = CoachManageFragment()
    }

    private lateinit var mCoachManageViewModel: CoachManageViewModel
    private val mCoachAdapter = CoachAdapter()
    override fun getLayoutId(): Int = R.layout.coach_manage_fragment

    override fun initView() {
        toolbar.title = getString(R.string.title_fragment_coach)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        recyclerCoach.apply {
            adapter = mCoachAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initViewModel() {
        mCoachManageViewModel = ViewModelProvider(this).get(CoachManageViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        mCoachManageViewModel.getCoach()
    }

    override fun observerForever() {
        mCoachManageViewModel.coachLiveData.observe(this, {
            if (it.isEmpty()) containerNoData.visible()
            else containerNoData.gone()
            mCoachAdapter.setData(it)
        })

        mCoachManageViewModel.mLoading.observe(this, {
            if (it) showLoading()
            else hideLoading()
        })
    }

    override fun observerOnce() {
        //Nothing
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        mCoachAdapter.onClickCoachListener = {
            val coachDetailManageFragment = CoachDetailManageFragment.newInstance(it)
            pushFragment(coachDetailManageFragment)
        }

        fabAddCoach.setOnClickListener {
            val coachDetailManageFragment = CoachDetailManageFragment.newInstance()
            pushFragment(coachDetailManageFragment)
        }
    }
}