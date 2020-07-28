package com.example.movticket.Buying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movticket.Home.Dashboard.playAdapter
import com.example.movticket.Model.Film
import com.example.movticket.Model.Plays
import com.example.movticket.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private var datalist = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data : Film = intent.getParcelableExtra<Film>("data")
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data.judul.toString())
            .child("play")



        tv_judul.text = data.judul
        tv_genre.setText(data.genre) //bentuk lain
        tv_desc.text = data.desc
        tv_rating.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(img_poster)

        rv_who.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getdata()

        btn_pilih_bangku.setOnClickListener {
            startActivity(Intent(this, PilihBangkuActivity::class.java).putExtra("data", data))
        }

    }

    private fun getdata() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DetailActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                datalist.clear()

                for (getDataSnapshot in dataSnapshot.children){
                    var play = getDataSnapshot.getValue(Plays::class.java)
                    datalist.add(play!!)
                }

                rv_who.adapter = playAdapter(datalist){
                }
            }

        })
    }
}