package com.example.movticket.Buying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Film
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.example.movticket.wallet.model.wallet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    var total=0
    var saldo = 0
    var username = ""
    var transactionCount = 0
    var ticketCount = 0
    var judul = ""
    lateinit var film : Film
    lateinit var preferences : Prefences

    var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Prefences(this)
        saldo = preferences.getValue("saldo").toString().toInt()
        username = preferences.getValue("username").toString()
        transactionCount = preferences.getValue("transactionCount")!!.toInt()
        ticketCount = preferences.getValue("ticketCount")!!.toInt()

        film = intent.getParcelableExtra("film")

        judul = film.judul.toString()
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (i in dataList.indices){
            total += dataList[i].harga!!.toInt()
        }

        dataList.add( Checkout("Total harus dibayar", total.toString()))

        rv_bangku.layoutManager = LinearLayoutManager(this)
        rv_bangku.adapter = CheckoutAdapter(dataList){
        }

        btn_back.setOnClickListener{
            finish()
        }

        btn_bayar.setOnClickListener {
            updatesaldo()

            finishAffinity()
            startActivity(Intent(this, SuccessBuyingActivity::class.java))
        }

        btn_batal.setOnClickListener {
            finish()

        }


    }

    private fun updatesaldo() {
        if (saldo >= total){
            saldo-=total;

            // update shared preference
            transactionCount += 1
            preferences.setValue("saldo",saldo.toString())
            preferences.setValue("transactionCount",transactionCount.toString())

            //update firebase
            var map = mutableMapOf<String, Int>()
            map["saldo"] = saldo
            FirebaseDatabase.getInstance().reference
                .child("User")
                .child(username)
                .updateChildren(map as Map<String, Any>)

            pushQueue(total) //push transaksi ke firebase
            pushFilm() //push tiket ke history

        } else{
            Toast.makeText(this, "Saldo anda tidak mencukupi", Toast.LENGTH_LONG )
                .show()
        }
    }


    private fun pushQueue(total: Int) {

        var mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("User")
            .child(username)
            .child("Transaksi")
            .child("$transactionCount")

        mDatabaseReference.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    //Toast.makeText(this, ""+p0.message, Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    mDatabaseReference.setValue(
                        wallet(
                            "$judul",
                            "Jumat, 20 Januari 2020",
                            money = total.toDouble(),
                            status = "0"
                        )
                    )
                }

            }
        )
    }

    private fun pushFilm() {
        ticketCount++
        var mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("User")
            .child(username)
            .child("Tiket")
            .child("$ticketCount")

        mDatabaseReference.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    //TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    mDatabaseReference.setValue(film)
                    preferences.setValue("ticketCount",ticketCount.toString())
                }

            }
        )

    }
}