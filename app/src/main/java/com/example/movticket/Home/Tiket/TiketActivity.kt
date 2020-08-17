package com.example.movticket.Home.Tiket

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Film
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_tiket.*
import kotlinx.android.synthetic.main.pop_up_view.*
import java.util.zip.Inflater

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private lateinit var dialog : Dialog

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

        dataList.clear()
        dataList.add(Checkout("A1", ""))
        dataList.add(Checkout("A2", ""))

        rv_seats.layoutManager = LinearLayoutManager(this)
        rv_seats.adapter = TiketAdapter(dataList){

        }

        btn_back.setOnClickListener {
            finish()
        }

        img_popup.setOnClickListener{

            dialog = Dialog(this)
            var inflater : LayoutInflater = getLayoutInflater();
            var dialogView = inflater.inflate(R.layout.pop_up_view, null);
            dialog.setContentView(dialogView);
            dialog.setCancelable(false);
            dialog.setTitle("Form Biodata");

            pop_tutup?.setOnClickListener {
                Log.d("Teteh", "Kimi wo doko desu ka?")
                dialog.cancel()
            }
            dialog.show()
        }

    }


}