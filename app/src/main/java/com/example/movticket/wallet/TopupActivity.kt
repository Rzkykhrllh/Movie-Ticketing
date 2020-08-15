package com.example.movticket.wallet

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.movticket.Home.HomeActivity
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_topup.*
import kotlinx.android.synthetic.main.activity_topup.btn_topup
import kotlinx.android.synthetic.main.activity_my_wallet.tv_saldo as tv_saldo1

class TopupActivity : AppCompatActivity() {

    var top10k = false
    var top20k = false
    var top25k = false
    var top50k = false
    var top100k = false
    var top200k = false

    var saldo_sekarang: Int = 0
    lateinit var preference: Prefences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup)


        preference = Prefences(this!!)
        var username = preference.getValue("username")!!
        saldo_sekarang = preference.getValue("saldo").toString().toInt()
        tv_saldo.text = saldo_sekarang.toString()

        //readSingle(username)
        Log.d("saldo luar", "$saldo_sekarang")


        btn_topup.setOnClickListener {
            updatesaldo(username)
        }

        btn_10k.setOnClickListener {
            if (top10k) {
                deselect(btn_10k, 10)
            } else {
                select(btn_10k, 10)
            }
        }
        btn_20k.setOnClickListener {
            if (top20k) {
                deselect(btn_20k, 20)
            } else {
                select(btn_20k, 20)
            }
        }
        btn_25k.setOnClickListener {
            if (top25k) {
                deselect(btn_25k, 25)
            } else {
                select(btn_25k, 25)
            }
        }
        btn_50k.setOnClickListener {
            if (top50k) {
                deselect(btn_50k, 50)
            } else {
                select(btn_50k, 50)
            }
        }
        btn_100k.setOnClickListener {
            if (top100k) {
                deselect(btn_100k, 100)
            } else {
                select(btn_100k, 100)
            }
        }
        btn_200k.setOnClickListener {
            if (top200k) {
                deselect(btn_200k, 200)
            } else {
                select(btn_200k, 200)
            }
        }

        btn_backk.setOnClickListener {
            finish()
        }


    }

    private fun select(now: TextView, nominal: Int) {
        now.setTextColor(resources.getColor(R.color.pink))
        now.setBackgroundResource(R.drawable.shape_rectangle_line_pink)

        when (nominal) {
            10 -> top10k = true
            20 -> top20k = true
            25 -> top25k = true
            50 -> top50k = true
            100 -> top100k = true
            200 -> top200k = true
            else -> ""
        }
        activeate_tombol()

    }

    private fun activeate_tombol() {
        if (top10k || top20k || top25k || top50k || top100k || top100k) {
            btn_topup.visibility = View.VISIBLE
        } else {
            btn_topup.visibility = View.INVISIBLE
        }
    }

    private fun deselect(now: TextView, nominal: Int) {
        now.setTextColor(resources.getColor(R.color.putih))
        now.setBackgroundResource(R.drawable.shape_rectangle_line_putih)

        when (nominal) {
            10 -> top10k = false
            20 -> top20k = false
            25 -> top25k = false
            50 -> top25k = false
            100 -> top100k = false
            200 -> top200k = false
            else -> ""
        }
        activeate_tombol()
    }

    private fun updatesaldo(username: String) {
        var total = 0

        if (top10k) total += 10000
        if (top20k) total += 20000
        if (top25k) total += 25000
        if (top50k) total += 50000
        if (top100k) total += 100000
        if (top200k) total += 200000


        //build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        //set message
        dialogBuilder.setMessage("Apakah anda yakin ingin Top Up sebesar $total ?")

        //menidakan tombol netral
        dialogBuilder.setCancelable(false)

        //tombol positive
        dialogBuilder.setPositiveButton("Ya", DialogInterface.OnClickListener{ dialog, id ->
                var map = mutableMapOf<String, Int>()
                map["saldo"] = saldo_sekarang + total
                FirebaseDatabase.getInstance().reference
                    .child("User")
                    .child(username)
                    .updateChildren(map as Map<String, Any>)
                preference.setValue("saldo", (saldo_sekarang + total).toString())

                finishAffinity()
                startActivity(Intent(this, SuccessTopupActivity::class.java))
            })

        //tombol negative
        dialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener{
                dialog, id -> dialog.cancel()
            })

        //membuat dialog
        val alert = dialogBuilder.create()

        //set judul
        alert.setTitle("Konfirmasi")

        //menampilkan
        alert.show()

    }

}