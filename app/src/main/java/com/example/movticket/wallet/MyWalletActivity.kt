package com.example.movticket.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.example.movticket.wallet.adapter.WalletAdapter
import com.example.movticket.wallet.model.wallet
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWalletActivity : AppCompatActivity() {

    lateinit var preference : Prefences
    private var dataList = arrayListOf<wallet>()
    private lateinit var mDatabase : DatabaseReference
    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        mDatabase = FirebaseDatabase.getInstance().getReference()

        preference = Prefences(this!!)
        tv_saldo.text = preference.getValue("saldo")
        username  = preference.getValue("username")!!

        /*
        UPDATE NILAI DARI SALDO SUKSES, BISA DI IMPLEMENT DI SETELAH POP UP
        var map = mutableMapOf<String, Int>()
        map["saldo"] = 999
        FirebaseDatabase.getInstance().reference
            .child("User")
            .child(username)
            .updateChildren(map as Map<String, Any>)
            */

        readSingle()

        dataList.add(
            wallet(
                "Spider man : Far From Home",
                "Kamis, 12 Januari 2020",
                35000.0,
                "0"
            )
        )

        dataList.add(
            wallet(
                "Top Up",
                "Kamis, 19 Januari 2020",
                200000.0,
                "1"
            )
        )

        dataList.add(
            wallet(
                "Nanti Kita Cerita Tentang Hari Ini",
                "Kamis, 19 Januari 2020",
                105000.0,
                "0"
            )
        )

        dataList.add(
            wallet(
                "Violet Evegarden The Movie",
                "Kamis, 21 Juli 2020",
                70000.0,
                "0"
            )
        )

        rv_transaksi.layoutManager = LinearLayoutManager(this)
        rv_transaksi.adapter = WalletAdapter(dataList){

        }

        btn_topup.setOnClickListener {
            startActivity(Intent(this, TopupActivity::class.java))
        }

    }

    private fun readSingle() {
        FirebaseDatabase.getInstance().reference
            .child("User")
            .child(username)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var map = p0.value as Map<String,Int>
                        tv_saldo.text = map["saldo"].toString()

                    }

                }
            )

    }
}