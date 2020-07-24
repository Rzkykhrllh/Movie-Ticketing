package com.example.movticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.movticket.onboarding.OnBoardingOneActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var handler = Handler() //1. buat handler dulu
        handler.postDelayed({ //2. jalanin handlernya
            //apa yang terjadi setelah delay
            var intent = Intent(this@SplashScreenActivity, OnBoardingOneActivity::class.java)
            startActivity(intent)
            finish() //destroy activity splashscreen, supaya gak bisa dipencet back

            //dibawah itu ada waktu delaynta, 5 detik
        }, 1000 )

    }
}
