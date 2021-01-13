package com.example.coachticketbookingforbusiness.screen.coach_detail_manage

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.base.BaseFragment
import com.example.coachticketbookingforbusiness.model.Coach
import com.example.coachticketbookingforbusiness.screen.user_detail_manage.UserDetailManageFragment
import com.example.coachticketbookingforbusiness.utils.Constants
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.coach_detail_manage_fragment.*

class CoachDetailManageFragment : BaseFragment() {

    companion object {
        private const val KEY_DRIVER_ID = "ID"
        private const val MODE_ADD_NEW = "ADD_NEW"
        private const val MODE_EDIT = "EDIT"
        fun newInstance(id: String? = null) = CoachDetailManageFragment().apply {
            id?.let {
                val bundle = Bundle().also {
                    it.putString(KEY_DRIVER_ID, id)
                }
                arguments = bundle
            }
        }
    }

    private var mCurrentMode = MODE_ADD_NEW
    private var mCurrentId = Constants.EMPTY_STRING
    private lateinit var mCoachDetailManageViewModel: CoachDetailManageViewModel
    override fun getLayoutId(): Int = R.layout.coach_detail_manage_fragment

    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
    }

    override fun initViewModel() {
        mCoachDetailManageViewModel =
            ViewModelProvider(this).get(CoachDetailManageViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (mCurrentMode == MODE_ADD_NEW) inflater.inflate(R.menu.menu_add_coach, menu)
        else inflater.inflate(R.menu.menu_edit_coach, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun initData(bundle: Bundle?) {
        bundle?.let {
            mCurrentId = it.getString(KEY_DRIVER_ID, Constants.EMPTY_STRING)
            mCurrentMode = if (mCurrentId == Constants.EMPTY_STRING) MODE_ADD_NEW else MODE_EDIT
        }


        context?.let {
            val arrayAdapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                arrayListOf(
                    "Giường nằm 46 chỗ 2 tầng", "Xe 29 chỗ"
                )
            )
            spinnerCoach.adapter = arrayAdapter
        }

        if (mCurrentMode == MODE_EDIT) {
            toolbar.title = getString(R.string.title_edit_coach)
            mCoachDetailManageViewModel.getCoach(mCurrentId)
        } else {
            toolbar.title = getString(R.string.title_add_coach)
        }

    }

    override fun observerForever() {
        mCoachDetailManageViewModel.coachLiveData.observe(this, {
            setData(it)
        })

        mCoachDetailManageViewModel.updateResultLiveDate.observe(this, {
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

        mCoachDetailManageViewModel.mLoading.observe(this, {
            if (it) showLoading()
            else hideLoading()
        })

        mCoachDetailManageViewModel.mError.observe(this, {
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

    override fun observerOnce() {
        //Nothing
    }

    private fun setData(coach: Coach?) {
        coach?.apply {
            edtCoachId.setText(id)
            edtCoachBrand.setText(carBrand)
            if (this.numberPosition == 46) {
                spinnerCoach.setSelection(0)
            } else {
                spinnerCoach.setSelection(1)
            }
            edtRate.setText(rate.toString())
            edtPhoneNumber.setText(driverId)
        }
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener { popBackStack() }
        btnApply.setOnClickListener {
            try {
                val oldId = mCurrentId
                val coach = getCoachInformation()
                if (mCurrentMode == MODE_EDIT) mCoachDetailManageViewModel.update(oldId, coach)
                else mCoachDetailManageViewModel.add(coach)
            } catch (e: java.lang.NumberFormatException) {
                context?.let {
                    Toasty.error(it, "Sai format number!!!", Toast.LENGTH_LONG, true).show()
                }
            }
        }
    }

    @Throws(NumberFormatException::class)
    private fun getCoachInformation(): Coach {
        val newId = edtCoachId.text.toString()
        val carBrand = edtCoachBrand.text.toString()
        val position: Int = if(spinnerCoach.selectedItemPosition == 0)  46 else 29
        val floor : Int = if(spinnerCoach.selectedItemPosition == 0) 2 else 1
        val rate = edtRate.text.toString().toFloat()
        val phone = edtPhoneNumber.text.toString()
        return Coach(
            id = newId,
            numberPosition = position,
            numberFloor = floor,
            driverId = phone,
            carBrand = carBrand,
            images = "",
            rate = rate
        )
    }
}