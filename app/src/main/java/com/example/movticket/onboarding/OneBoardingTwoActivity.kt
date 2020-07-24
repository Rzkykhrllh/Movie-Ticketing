package com.example.movticket.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movticket.R
import com.example.movticket.Sign.Signin
import kotlinx.android.synthetic.main.activity_one_boarding_two.*
import kotlinx.android.synthetic.main.activity_one_boarding_two.btn_lewati

class OneBoardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_boarding_two)


        btn_lanjutkan.setOnClickListener {
            var intent = Intent(this, OnBoardingThreeActivity::class.java)
            startActivity(intent)
        }

        btn_lewati.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, Signin::class.java))
        }
    }
}