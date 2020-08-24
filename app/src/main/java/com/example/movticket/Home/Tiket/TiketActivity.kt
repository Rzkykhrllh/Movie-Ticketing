package com.example.movticket.Home.Tiket

import android.app.Dialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Script
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Tiket
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_tiket.*
import kotlinx.android.synthetic.main.pop_up_view.*
import java.util.zip.Inflater

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private lateinit var dialog : Dialog
    lateinit var preferences: Prefences
    var username = ""
    var ticketCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        val data : Tiket = intent.getParcelableExtra<Tiket>("data")
        tv_title.text = data.judul
        tv_genre.text = data.genre
        tv_rate.text = data.rating
        Log.d("t", "for teteh")

        preferences = Prefences(this)
        username = preferences.getValue("username").toString()
        ticketCount = preferences.getValue("ticketCount")!!.toInt()

        Glide.with(this)
            .load(data.poster)
            .into(img_poster)

        dataList.clear()


        rv_seats.layoutManager = LinearLayoutManager(this)
        getKursi(data.id!!.toInt())


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

                private fun getKursi(index : Int) {
                    var mDatabaseReference = FirebaseDatabase.getInstance().reference
                        .child("User")
                        .child("$username")
                        .child("Kursi")
                        .child("$index")

                    mDatabaseReference.addListenerForSingleValueEvent(
                        object : ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                for (i in p0.children){
                                    var kursi = i.getValue(Checkout::class.java)
                                    dataList.add(kursi!!)
                                }

                                rv_seats.adapter = KursiAdapter(dataList){

                    }
                }

            }
        )
    }


}