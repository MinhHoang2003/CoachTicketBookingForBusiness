package com.example.coachticketbookingforbusiness.screen.route_detail_manage

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.base.DebugLog
import com.example.coachticketbookingforbusiness.base.view.invisible
import com.example.coachticketbookingforbusiness.base.view.visible
import com.example.coachticketbookingforbusiness.dialog.choose_coach.DialogChooseCoach
import com.example.coachticketbookingforbusiness.model.Route
import com.example.coachticketbookingforbusiness.model.RouteBody
import com.example.coachticketbookingforbusiness.screen.location_manage.LocationManageFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.route_detail_manage_fragment.*

class RouteDetailManageFragment : BaseFragment() {

    companion object {
        private const val KEY_ROUTE_ID = "ID"
        private const val MODE_ADD_NEW = "ADD_NEW"
        private const val MODE_EDIT = "EDIT"
        fun newInstance(id: Int) = RouteDetailManageFragment().apply {
            id.let {
                val bundle = Bundle().also {
                    it.putInt(KEY_ROUTE_ID, id)
                }
                arguments = bundle
            }
        }
    }

    private var mCurrentMode = MODE_ADD_NEW
    private var mCurrentId = -1
    private lateinit var mRouteDetailManageViewModel: RouteDetailManageViewModel
    private var coachSelectListener: ((String) -> Unit)? = null
    override fun getLayoutId(): Int = R.layout.route_detail_manage_fragment
    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
    }

    override fun initViewModel() {
        mRouteDetailManageViewModel =
            ViewModelProvider(this).get(RouteDetailManageViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        bundle?.apply {
            mCurrentId = getInt(KEY_ROUTE_ID, mCurrentId)
            mCurrentMode = if (mCurrentId == -1) MODE_ADD_NEW else MODE_EDIT
            bundle.clear()
        }
        if (mCurrentMode == MODE_EDIT || mCurrentId != -1) {
            toolbar.title = "Sửa thông tin tuyến xe"
            mRouteDetailManageViewModel.getRoute(mCurrentId)
        } else {
            toolbar.title = "Thêm tuyến xe"
        }
        if (mCurrentId == -1) layoutLocation.invisible()
        else {
            textRouteId.text = mCurrentId.toString()
        }
    }

    override fun observerForever() {
        mRouteDetailManageViewModel.routeLiveData.observe(this, {
            setupData(it)
        })

        mRouteDetailManageViewModel.mLoading.observe(this, {
            if (it) showLoading() else hideLoading()
        })
    }

    override fun observerOnce() {
        mRouteDetailManageViewModel.insertResultLiveDate.observe(this, {
            if (it != null && it != -1) {
                mCurrentId = it
                layoutLocation.visible()
                textRouteId.text = mCurrentId.toString()
                mCurrentMode = MODE_EDIT
                context?.apply {
                    Toasty.success(
                        this,
                        "Thêm thông tin thành công!!!",
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            }
        })

        mRouteDetailManageViewModel.updateResultLiveDate.observe(this, object :
            Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t == true) {
                    context?.apply {
                        Toasty.success(
                            this,
                            "Cập nhật thông tin thành công!!!",
                            Toast.LENGTH_LONG,
                            true
                        ).show()
                    }
                    mRouteDetailManageViewModel.updateResultLiveDate.removeObserver(this)
                }
            }

        })
    }

    private fun setupData(route: Route) {
        textRouteId.text = route.id.toString()
        txtCoachId.text = route.coachId
        textStartTime.text = route.startTime
        textEndTime.text = route.endTime
        edtRate.setText(route.price.toString())
    }

    private fun getRoute(): RouteBody {
        return RouteBody(
            id = mCurrentId,
            coachId = txtCoachId.text.toString(),
            startTime = textStartTime.text.toString(),
            endTime = textEndTime.text.toString(),
            price = edtRate.text.toString().toInt()
        )
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener { popBackStack() }
        btnApply.setOnClickListener {
            val route = getRoute()
            if (mCurrentMode == MODE_EDIT) {
                mRouteDetailManageViewModel.update(mCurrentId, route)
            } else {
                mRouteDetailManageViewModel.add(route)
            }
        }

        layoutLocation.setOnClickListener {
            val locationManageFragment = LocationManageFragment.newInstance(mCurrentId)
            pushFragment(locationManageFragment)
        }

        layoutCoachId.setOnClickListener {
            val fmn = childFragmentManager
            val dialog = DialogChooseCoach()
            dialog.show(fmn, "dia")
            dialog.coachSelectedListener = {
                txtCoachId.text = it
            }
        }
    }

}