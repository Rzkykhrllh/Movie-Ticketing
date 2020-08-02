package com.example.movticket.Buying

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.Model.Checkout
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    var total=0
    lateinit var preferences : Prefences
    var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Prefences(this)
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
    }
}