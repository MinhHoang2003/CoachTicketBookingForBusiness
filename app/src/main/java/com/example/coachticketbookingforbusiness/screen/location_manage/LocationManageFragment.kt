package com.example.coachticketbookingforbusiness.screen.location_manage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.LocationAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.base.DebugLog
import com.example.coachticketbookingforbusiness.base.view.gone
import com.example.coachticketbookingforbusiness.base.view.visible
import com.example.coachticketbookingforbusiness.screen.location_detail_manage.LocationDetailManageFragment
import com.example.coachticketbookingforbusiness.utils.ToastUtils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.location_manage_fragment.*

class LocationManageFragment : BaseFragment() {

    companion object {

        private const val KEY_ROUTE_ID = "ROUTE_ID"

        fun newInstance(id: Int) = LocationManageFragment().apply {
            val bundle = Bundle().also {
                it.putInt(KEY_ROUTE_ID, id)
            }
            arguments = bundle
        }
    }

    private lateinit var mLocationManageViewModel: LocationManageViewModel
    private val mLocationAdapter = LocationAdapter()
    private var mCurrentRouteId = -1
    override fun getLayoutId(): Int = R.layout.location_manage_fragment

    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        mLocationAdapter.isModeManager = true
        recyclerLocations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mLocationAdapter
        }
    }

    override fun initViewModel() {
        mLocationManageViewModel = ViewModelProvider(this).get(LocationManageViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        bundle?.let {
            mCurrentRouteId = it.getInt(KEY_ROUTE_ID, -1)
            if (id != -1) {
                toolbar.title = "Điểm dừng cho tuyến xe: $mCurrentRouteId"
                mLocationManageViewModel.getLocationByRouteId(mCurrentRouteId)
            }
        }
    }

    override fun observerForever() {
        mLocationManageViewModel.locationsLiveData.observe(this, {
            if (it.isEmpty()) containerNoData.visible()
            else containerNoData.gone()
            mLocationAdapter.setData(it)
        })

        mLocationManageViewModel.mLoading.observe(this, {
            if (it) showLoading() else hideLoading()
        })
    }

    override fun observerOnce() {
        mLocationManageViewModel.isDeleted.observe(this, {
            if(it) {
                context?.apply {
                    Toasty.success(this, "Xóa điểm dừng thành công.", Toast.LENGTH_SHORT, true).show()
                }

                mLocationManageViewModel.getLocationByRouteId(mCurrentRouteId)
            }
        })

        mLocationManageViewModel.mError.observe(this, {
            if (it.isNotBlank()) ToastUtils.showError(context, it)
        })
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener { popBackStack() }
        mLocationAdapter.onClickLocation = {
            val locationDetailManageFragment = LocationDetailManageFragment.newInstance(
                it,
                mCurrentRouteId,
                LocationDetailManageFragment.MODE_EDIT
            )
            pushFragment(locationDetailManageFragment)
        }

        fabAddLocation.setOnClickListener {
            val locationDetailManageFragment =
                LocationDetailManageFragment.newInstance(
                    -1,
                    mCurrentRouteId,
                    LocationDetailManageFragment.MODE_ADD_NEW
                )
            pushFragment(locationDetailManageFragment)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return if ( item.itemId == LocationAdapter.ID_DELETE) {
            mLocationManageViewModel.removeLocation(mLocationAdapter.getLocationId(item.groupId))
            true
        } else super.onContextItemSelected(item)
    }

}