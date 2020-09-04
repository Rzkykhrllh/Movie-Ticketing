package com.example.movticket.Buying

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Film
import com.example.movticket.Model.Tiket
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
    var currentApiVersion = 0

    var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentApiVersion = Build.VERSION.SDK_INT
        val flags: Int = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = flags
            val decorView: View = window.decorView
            decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN === 0) {
                    decorView.systemUiVisibility = flags
                }
            }
        }
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
            updatesaldo() //proses pengurangan saldo disini

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
            transactionCount++
            ticketCount++

            preferences.setValue("saldo",saldo.toString())
            preferences.setValue("transactionCount",transactionCount.toString())
            preferences.setValue("ticketCount",ticketCount.toString())


            //update saldo di firebase
            var map = mutableMapOf<String, Int>()
            map["saldo"] = saldo
            map["transactionCount"] = transactionCount
            map["ticketCount"] = ticketCount

            FirebaseDatabase.getInstance().reference
                .child("User")
                .child(username)
                .updateChildren(map as Map<String, Any>)

            pushQueue(total) //push transaksi ke firebase
            //pushKursi() //push kursi ke history
            pushTiket() //push tiket ke history


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

    private fun pushTiket() {
        var mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("User")
            .child(username)
            .child("Tiket")
            .child("$ticketCount")

        Log.d("Tiket", "1")

        mDatabaseReference.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    //TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {

                    Log.d("Tiket", "2")
                    mDatabaseReference.setValue(
                        Tiket(
                            film.judul,
                            film.rating,
                            film.genre,
                            film.poster,
                            "$ticketCount"
                        )
                    )
                    Log.d("Tiket", "3")
                }

            }
        )
    }
    private fun pushKursi() {
        var mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("User")
            .child(username)
            .child("Kursi")
            .child("$ticketCount")

        Log.d("kursi", "1")

        mDatabaseReference.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    //TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {

                    Log.d("kursi", "2")

                    dataList.removeAt(dataList.size-1)
                    mDatabaseReference.setValue(dataList)

                    Log.d("kursi", "3")
                }

            }
        )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }
}
