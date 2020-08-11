package com.example.movticket.wallet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movticket.Home.HomeActivity
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_topup.*

class TopupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup)



        btn_topup.setOnClickListener {
            startActivity(Intent(this, SuccessTopupActivity::class.java))
        }
        
    }




}