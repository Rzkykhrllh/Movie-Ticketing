package com.example.movticket.Home

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movticket.Home.Dashboard.DashboardFragment
import com.example.movticket.Home.Tiket.TiketFragment
import com.example.movticket.Home.setting.setting
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_home.*

class FragmentAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {

    private val pages = listOf(
        DashboardFragment(),
        TiketFragment(),
        setting()
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->  DashboardFragment()
            1 ->  TiketFragment()
            2 ->  setting()
            else -> DashboardFragment()
        }
    }

    override fun getCount(): Int {
        return pages.size
    }

}