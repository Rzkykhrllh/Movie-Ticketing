package com.example.movticket.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_topup.*

class SuccessTopupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_topup)

        btn_topup.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, SuccessTopupActivity::class.java))
        }
    }
}