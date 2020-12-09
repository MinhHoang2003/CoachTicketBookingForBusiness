package com.example.coachticketbookingforbusiness.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.coachticketbookingforbusiness.screen.location.LocationFragment
import com.example.coachticketbookingforbusiness.screen.ticket.TicketFragment

class LocationPagerAdapter(
    private val routeId: Int,
    private val date: String,
    fmn: FragmentManager
) :
    FragmentStatePagerAdapter(fmn, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TicketFragment.newInstance(routeId, date)
            else -> LocationFragment.newInstance(routeId, date)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Vé xe"
            else -> "Điểm dừng"
        }
    }
}