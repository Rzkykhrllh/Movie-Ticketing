package com.example.movticket.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movticket.R
import com.example.movticket.Sign.Signin
import com.example.movticket.utils.Prefences
import kotlinx.android.synthetic.main.activity_on_boarding_one.*

class OnBoardingOneActivity : AppCompatActivity() {

    lateinit var preference : Prefences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_one)


        //buat ngecek apakah sudah pernah liat onboarding sebelumnya, apabila udah, maka di skip nanti
        preference = Prefences(this)
        if (preference.getValue("Onboarding").equals("1")){
            finishAffinity()
            startActivity(Intent(this@OnBoardingOneActivity, Signin::class.java))
        }

        //1. buat listener dari masing-masing button
        //2. buat intent masing2


        btn_lanjutkan.setOnClickListener{
            var intent = Intent(this, OneBoardingTwoActivity::class.java)
            startActivity(intent)

        }

        btn_lewati.setOnClickListener {
            finishAffinity() //biar pas di halaman sign in gak bisa di back, hapus semua activity yang pernah ada
            startActivity(Intent(this, Signin::class.java)) //versi malas buat variabel
        }
    }
}
