package com.example.coachticketbookingforbusiness.screen.location

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.adapter.LocationAdapter
import com.example.coachticketbookingforbusiness.base.BaseFragment
import kotlinx.android.synthetic.main.location_fragment.*

class LocationFragment : BaseFragment() {

    companion object {

        private const val KEY_ROUTE_ID = "ID"
        private const val KEY_DATE = "DATE"

        fun newInstance(routeId: Int, date: String) = LocationFragment().apply {
            val bundle = Bundle()
            bundle.putInt(LocationFragment.KEY_ROUTE_ID, id)
            bundle.putString(LocationFragment.KEY_DATE, date)
            arguments = bundle
        }
    }

    private lateinit var viewModel: LocationViewModel
    private var mPickLocationAdapter = LocationAdapter()
    private var mDestinationLocationAdapter = LocationAdapter()
    override fun getLayoutId(): Int = R.layout.location_fragment
    override fun initView() {
        recyclerPickLocation.apply {
            adapter = mPickLocationAdapter
            layoutManager = LinearLayoutManager(context)
        }

        recyclerDestinationLocation.apply {
            adapter = mDestinationLocationAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
    }

    override fun initData(bundle: Bundle?) {
        viewModel.getPickLocationWithTickets(1, "2020-11-15")
        viewModel.getDestinationLocationOfRoute(1, "2020-11-15")
    }

    override fun observerForever() {
        viewModel.pickLocationLiveData.observe(this, {
            mPickLocationAdapter.setData(it)
        })

        viewModel.destinationLocationLiveData.observe(this, {
            mDestinationLocationAdapter.setData(it)
        })
    }

    override fun observerOnce() {
    }

    override fun initListener() {
    }

}