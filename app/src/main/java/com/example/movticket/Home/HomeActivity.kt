package com.example.movticket.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.movticket.Home.Dashboard.*
import com.example.movticket.Home.Tiket.TiketFragment
import com.example.movticket.Home.setting.setting
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //set default fragment
        val fragmentHome = DashboardFragment()
        changeIcon(btn_menu1, R.drawable.menu_true)
        setFragment(fragmentHome)

        btn_menu1.setOnClickListener {
            setFragment(DashboardFragment())
            changeIcon(btn_menu1, R.drawable.menu_true)
            changeIcon(btn_menu2, R.drawable.tiket_false)
            changeIcon(btn_menu3, R.drawable.profile_false)
        }

        btn_menu2.setOnClickListener {
            setFragment(TiketFragment())
            changeIcon(btn_menu1, R.drawable.menu_false)
            changeIcon(btn_menu2, R.drawable.tiket_true)
            changeIcon(btn_menu3, R.drawable.profile_false)
        }

        btn_menu3.setOnClickListener {
            setFragment(setting())
            changeIcon(btn_menu1, R.drawable.menu_false)
            changeIcon(btn_menu2, R.drawable.tiket_false)
            changeIcon(btn_menu3, R.drawable.profile_true)
        }


    }

    private fun setFragment(fragment_tujuan: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransient = fragmentManager.beginTransaction()
        fragmentTransient.replace(R.id.frame_layout, fragment_tujuan) //masang fragment baru ke frame_layout
        fragmentTransient.commit()


    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}