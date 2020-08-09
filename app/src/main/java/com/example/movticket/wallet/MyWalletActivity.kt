package com.example.movticket.wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.R
import com.example.movticket.wallet.adapter.WalletAdapter
import com.example.movticket.wallet.model.wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWalletActivity : AppCompatActivity() {

    private var dataList = arrayListOf<wallet>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

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

    }
}