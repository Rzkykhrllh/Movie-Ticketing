package com.example.movticket.Buying


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movticket.R
import com.example.movticket.Sign.Signin
import com.example.movticket.onboarding.OneBoardingTwoActivity
import com.example.movticket.utils.Prefences
import kotlinx.android.synthetic.main.activity_on_boarding_one.*
import kotlinx.android.synthetic.main.activity_success_buying.*

class SuccessBuyingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_buying)
    }

}