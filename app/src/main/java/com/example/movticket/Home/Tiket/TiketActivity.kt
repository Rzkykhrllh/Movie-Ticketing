package com.example.movticket.Home.Tiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Film
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_tiket.*

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        val data : Film = intent.getParcelableExtra<Film>("data")
        tv_title.text = data.judul
        tv_genre.text = data.genre
        tv_rate.text = data.rating
        Log.d("t", "for teteh")

        Glide.with(this)
            .load(data.poster)
            .into(img_poster)

        dataList.add(Checkout("A1", ""))
        dataList.add(Checkout("A2", ""))

        rv_seats.adapter = TiketAdapter(dataList){

        }

    }
}