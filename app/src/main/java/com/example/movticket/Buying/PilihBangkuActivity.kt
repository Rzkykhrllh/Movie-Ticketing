package com.example.movticket.Buying

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Film
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA1 = false
    var statusA2 = false
    var total = 0

    private var datalist = ArrayList<Checkout>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data : Film = intent.getParcelableExtra<Film>("data")
        tv_judul.text = data.judul.toString()
    }
}