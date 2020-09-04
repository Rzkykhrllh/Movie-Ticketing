package com.example.movticket.wallet

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.example.movticket.wallet.model.wallet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_topup.*
import kotlinx.android.synthetic.main.activity_topup.btn_topup

class TopupActivity : AppCompatActivity() {

    var top10k = false
    var top20k = false
    var top25k = false
    var top50k = false
    var top100k = false
    var top200k = false

    var saldo_sekarang: Int = 0
    var transactionCount = 0
    var username = ""
    lateinit var preference: Prefences
    var currentApiVersion = 0

    lateinit var input : String

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

        setContentView(R.layout.activity_topup)


        preference = Prefences(this!!)

        username = preference.getValue("username")!!
        transactionCount = preference.getValue("transactionCount").toString().toInt()
        saldo_sekarang = preference.getValue("saldo").toString().toInt()
        tv_saldo.text = saldo_sekarang.toString()

        //readSingle(username)
        Log.d("saldo luar", "$saldo_sekarang")

        et_number.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (et_number.text.toString().isNotEmpty()){
                        hideAll()
                        btn_topup.visibility = View.VISIBLE
                    } else{
                       showAll()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //TODO("Not yet implemented")
                }

            }
        )

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

    private fun showAll(){
        btn_10k.visibility = View.VISIBLE
        btn_20k.visibility = View.VISIBLE
        btn_25k.visibility = View.VISIBLE
        btn_50k.visibility = View.VISIBLE
        btn_100k.visibility = View.VISIBLE
        btn_200k.visibility = View.VISIBLE
    }

    private fun hideAll() {
        btn_10k.visibility = View.INVISIBLE
        btn_20k.visibility = View.INVISIBLE
        btn_25k.visibility = View.INVISIBLE
        btn_50k.visibility = View.INVISIBLE
        btn_100k.visibility = View.INVISIBLE
        btn_200k.visibility = View.INVISIBLE

        deselect(btn_10k,10)
        deselect(btn_20k,20)
        deselect(btn_25k,25)
        deselect(btn_50k,50)
        deselect(btn_100k,100)
        deselect(btn_200k,200)

    }

    private fun activeate_tombol() {
        if (top10k || top20k || top25k || top50k || top100k || top100k || top200k) {
            btn_topup.visibility = View.VISIBLE
        } else {
            btn_topup.visibility = View.INVISIBLE
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

    private fun deselect(now: TextView, nominal: Int) {
        now.setTextColor(resources.getColor(R.color.putih))
        now.setBackgroundResource(R.drawable.shape_rectangle_line_putih)

        when (nominal) {
            10 -> top10k = false
            20 -> top20k = false
            25 -> top25k = false
            50 -> top50k = false
            100 -> top100k = false
            200 -> top200k = false
            else -> ""
        }
        activeate_tombol()
    }

    private fun updatesaldo(username: String) {
        var total = 0

        var input = et_number.text.toString()
        if (input!=""){
            total=input.toInt()
        }


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
        dialogBuilder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, id ->

            // Update saldo dan transaction_count pada database dan juga Shared_prefence

            transactionCount += 1
            var map = mutableMapOf<String, Int>()

            map["saldo"] = saldo_sekarang + total
            map["transactionCount"] = transactionCount

            Log.d("Transaksi", "$transactionCount")

            FirebaseDatabase.getInstance().reference //update saldo
                .child("User")
                .child(username)
                .updateChildren(map as Map<String, Any>)
            preference.setValue("saldo", (saldo_sekarang + total).toString())
            preference.setValue("transactionCount", transactionCount.toString())

            pushQueue(total)
            finishAffinity()
            startActivity(Intent(this, SuccessTopupActivity::class.java))
        })

        //tombol negative
        dialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, id ->
            dialog.cancel()
        })

        //membuat dialog
        val alert = dialogBuilder.create()

        //set judul
        alert.setTitle("Konfirmasi")

        //menampilkan
        alert.show()

    }

    //push data transaksi (top up ke dalam database)
    private fun pushQueue( total: Int) {

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
                            "Top Up",
                            "Kamis, 19 Januari 2020",
                            money = total.toDouble(),
                            status = "1"
                        )
                    )
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