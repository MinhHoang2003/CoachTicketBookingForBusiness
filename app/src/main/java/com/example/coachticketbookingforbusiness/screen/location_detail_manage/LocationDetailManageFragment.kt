  package com.example.coachticketbookingforbusiness.screen.location_detail_manage

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.model.Location
import com.example.coachticketbookingforbusiness.model.StopStationType
import com.example.coachticketbookingforbusiness.model.convertToName
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.location_detail_manage_fragment.*
import kotlinx.android.synthetic.main.location_detail_manage_fragment.toolbar
import java.util.*

class LocationDetailManageFragment : BaseFragment() {

    companion object {
        private const val KEY_ROUTE_ID = "ID"
        private const val KEY_LOCATION_ID = "LOCATION_ID"
        private const val KEY_MODE = "MODE"
        const val MODE_ADD_NEW = "ADD_NEW"
        const val MODE_EDIT = "EDIT"
        fun newInstance(id: Int, routeId: Int, mode: String) =
            LocationDetailManageFragment().apply {
                id.let {
                    val bundle = Bundle().also {
                        it.putInt(KEY_ROUTE_ID, routeId)
                        it.putInt(KEY_LOCATION_ID, id)
                        it.putString(KEY_MODE, mode)
                    }
                    arguments = bundle
                }
            }
    }

    private var mCurrentMode = MODE_ADD_NEW
    private var mCurrentId = -1
    private var mCurrentRouteId = -1
    private var mCurrentLocation: Location? = null
    private lateinit var mLocationDetailManageViewModel: LocationDetailManageViewModel
    private val mTimePickerDialog by lazy {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(rootActivity, { view, hourOfDay, minuteToday ->
                val date = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minuteToday)}"
                textTime?.text = date
            }, hour, minute, true)
    }
    override fun getLayoutId(): Int = R.layout.location_detail_manage_fragment

    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
    }

    override fun initViewModel() {
        mLocationDetailManageViewModel =
            ViewModelProvider(this).get(LocationDetailManageViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        bundle?.apply {
            mCurrentId = getInt(KEY_LOCATION_ID, -1)
            mCurrentMode = getString(KEY_MODE, MODE_ADD_NEW)
            mCurrentRouteId = getInt(KEY_ROUTE_ID, -1)
        }

        context?.let { context ->
            val arrayAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                StopStationType.values().map {
                    it.convertToName()
                }
            )
            spinnerStopStationType.adapter = arrayAdapter
        }


        if (mCurrentMode == MODE_EDIT) {
            toolbar.title = "Sửa thông tin điểm dừng"
            mLocationDetailManageViewModel.getLocationDetail(mCurrentId)
        } else {
            toolbar.title = "Thêm tuyến xe"
        }
    }

    override fun observerForever() {
        mLocationDetailManageViewModel.locationLiveData.observe(this, {
            mCurrentLocation = it
            setupData(it)
        })

        mLocationDetailManageViewModel.updateLiveData.observe(this, {
            if (it) {
                context?.let {
                    Toasty.success(
                        it,
                        "Cập nhật thông tin điểm dừng thành công!!!",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }
            }
        })

        mLocationDetailManageViewModel.mError.observe(this, { err ->
            if (err.isEmpty()) return@observe
            context?.let {
                Toasty.error(
                    it,
                    err,
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }
        })
    }

    override fun observerOnce() {
        //Nothing
    }

    private fun setupData(location: Location) {
        textTime.text = location.time
        edtCity.setText(location.city)
        edtDetailAddress.setText(location.detailLocation)
        edtLongitude.setText(location.longitude.toString())
        edtLatitude.setText(location.latitude.toString())
        spinnerStopStationType.setSelection(location.stopStationType - 1)
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener { popBackStack() }
        btnApply.setOnClickListener {
            val location = getLocation()
            location?.let {
                if (mCurrentMode == MODE_EDIT) mLocationDetailManageViewModel.updateLocation(it)
                else {
                    mLocationDetailManageViewModel.addLocation(it)
                }
            }
        }

        layoutTime.setOnClickListener {
            mTimePickerDialog.show()
        }

    }

    private fun getLocation(): Location? {
        return Location(
            time = textTime.text.toString(),
            id = mCurrentLocation?.id ?: -1,
            routeId = mCurrentRouteId,
            detailLocation = edtDetailAddress.text.toString(),
            city = edtCity.text.toString(),
            longitude = (edtLongitude.text.toString()).toFloat(),
            latitude = (edtLatitude.text.toString()).toFloat(),
            stopStationType = spinnerStopStationType.selectedItemPosition + 1
        )
    }
}