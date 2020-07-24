package com.example.movticket.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movticket.R
import com.example.movticket.Sign.Signin
import kotlinx.android.synthetic.main.activity_on_boarding_three.*

class OnBoardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_three)

        btn_mulai.setOnClickListener {
            startActivity(Intent(this, Signin::class.java))
            finishAffinity()
        }
    }
}