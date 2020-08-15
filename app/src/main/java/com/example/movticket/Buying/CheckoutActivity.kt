package com.example.movticket.Buying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.Model.Checkout
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    var total=0
    var saldo = 0
    var username = ""
    lateinit var preferences : Prefences

    var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Prefences(this)
        saldo = preferences.getValue("saldo").toString().toInt()
        username = preferences.getValue("username").toString()

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
        }

        btn_batal.setOnClickListener {
            finish()
        }


    }

    private fun updatesaldo() {
        if (saldo >= total){
            saldo-=total;

            // update shared preference
            preferences.setValue("saldo",saldo.toString())

            //update firebase
            var map = mutableMapOf<String, Int>()
            map["saldo"] = saldo
            FirebaseDatabase.getInstance().reference
                .child("User")
                .child(username)
                .updateChildren(map as Map<String, Any>)


            finishAffinity()
            startActivity(Intent(this, SuccessBuyingActivity::class.java))
        } else{
            Toast.makeText(this, "Saldo anda tidak mencukupi", Toast.LENGTH_LONG )
                .show()
        }
    }
}