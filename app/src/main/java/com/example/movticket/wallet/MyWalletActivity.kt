package com.example.movticket.wallet

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.Home.HomeActivity
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.example.movticket.wallet.adapter.WalletAdapter
import com.example.movticket.wallet.model.wallet
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_my_wallet.*
import java.text.NumberFormat
import java.util.*

class MyWalletActivity : AppCompatActivity() {

    lateinit var preference : Prefences
    private var dataList = arrayListOf<wallet>()
    private lateinit var mDatabase : DatabaseReference
    private lateinit var username : String
    var currentApiVersion = 0


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
        setContentView(R.layout.activity_my_wallet)

        mDatabase = FirebaseDatabase.getInstance().getReference()






        preference = Prefences(this!!)
        tv_saldo.text = preference.getValue("saldo").toString()
        username  = preference.getValue("username")!!

        rv_transaksi.layoutManager = LinearLayoutManager(this)
        getTransactionData() //take transaction data from database


        btn_topup.setOnClickListener {
            startActivity(Intent(this, TopupActivity::class.java))
        }

        btn_back.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    private fun getTransactionData() {
        var mDatabaseReference = FirebaseDatabase.getInstance().reference
            .child("User")
            .child(username)
            .child("Transaksi")
        
        mDatabaseReference.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    //TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    dataList.clear()
                    
                    for (data in p0.children){
                        var transaksi = data.getValue(wallet::class.java)
                        dataList.add(transaksi!!)
                    }
                    dataList.reverse()

                    rv_transaksi.adapter = WalletAdapter(dataList){
                    }
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

    /*private fun readSingle() {
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

    }*/
}