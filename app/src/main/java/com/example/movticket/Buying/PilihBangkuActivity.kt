package com.example.movticket.Buying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Film
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA1 = false
    var statusA2 = false
    var statusA3 = false
    var statusA4 = false
    var statusB1 = false
    var statusB2 = false
    var statusB3 = false
    var statusB4 = false
    var statusC1 = false
    var statusC2 = false
    var statusC3 = false
    var statusC4 = false
    var statusD1 = false
    var statusD2 = false
    var statusD3 = false
    var statusD4 = false
    var total = 0

    private var datalist = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data : Film = intent.getParcelableExtra<Film>("data")
        tv_judul.text = data.judul.toString()

        A1.setOnClickListener {
            Log.v("A1", "A1 dipencet")
            if (statusA1){
                deactive(A1, "A1")
            }else{
                active(A1, "A1")
            }
        }

        A2.setOnClickListener {
            if (statusA2){
                deactive(A2, "A2")
            }else{
                active(A2, "A2")
            }
        }

        A3.setOnClickListener {
            if (statusA3){
                deactive(A3, "A3")
            }else{
                active(A3, "A3")
            }
        }

        A4.setOnClickListener {
            if (statusA4){
                deactive(A4, "A4")
            }else{
                active(A4, "A4")
            }
        }

        B1.setOnClickListener {
            if (statusB1){
                deactive(B1, "B1")
            }else{
                active(B1, "B1")
            }
        }

        B2.setOnClickListener {
            if (statusB2){
                deactive(B2, "B2")
            }else{
                active(B2, "B2")
            }
        }
        B3.setOnClickListener {
            if (statusB3){
                deactive(B3, "B3")
            }else{
                active(B3, "B3")
            }
        }
        B4.setOnClickListener {
            if (statusB4){
                deactive(B4, "B4")
            }else{
                active(B4, "B4")
            }
        }
        C1.setOnClickListener {
            if (statusC1){
                deactive(C1, "C1")
            }else{
                active(C1, "C1")
            }
        }
        C2.setOnClickListener {
            if (statusC2){
                deactive(C2, "C2")
            }else{
                active(C2, "C2")
            }
        }
        C3.setOnClickListener {
            if (statusC3){
                deactive(C3, "C3")
            }else{
                active(C3, "C3")
            }
        }
        C4.setOnClickListener {
            if (statusC4){
                deactive(C4, "C4")
            }else{
                active(C4, "C4")
            }
        }
        D1.setOnClickListener {
            if (statusD1){
                deactive(D1, "D1")
            }else{
                active(D1, "D1")
            }
        }
        D2.setOnClickListener {
            if (statusD2){
                deactive(D2, "D2")
            }else{
                active(D2, "D2")
            }
        }
        D3.setOnClickListener {
            if (statusD3){
                deactive(D3, "D3")
            }else{
                active(D3, "D3")
            }
        }
        D4.setOnClickListener {
            if (statusD4){
                deactive(D4, "D4")
            }else{
                active(D4, "D4")
            }
        }



        btn_pilih.setOnClickListener {
            //datalist = datalist.sortedWith(compareBy({ it.kursi }))

            startActivity(Intent(this, CheckoutActivity::class.java).putExtra("data",datalist))
        }

        btn_back.setOnClickListener{
            finish()
        }
    }

    private fun active(gambar : ImageView, bangku:String){
        gambar.setImageResource(R.drawable.ic_selected)
        total++
        beliTiket(total)

        datalist.add(Checkout(bangku, "35000"))

        when (bangku){
            "A1" -> statusA1 = true
            "A2" -> statusA2 = true
            "A3" -> statusA3 = true
            "A4" -> statusA4 = true
            "B1" -> statusB1 = true
            "B2" -> statusB2 = true
            "B3" -> statusB3 = true
            "B4" -> statusB4 = true
            "C1" -> statusC1 = true
            "C2" -> statusC2 = true
            "C3" -> statusC3 = true
            "C4" -> statusC4 = true
            "D1" -> statusD1 = true
            "D2" -> statusD2 = true
            "D3" -> statusD3 = true
            "D4" -> statusD4 = true
            else -> ""
        }
    }

    private fun deactive(gambar : ImageView, bangku:String){
        gambar.setImageResource(R.drawable.ic_empty)
        total--
        beliTiket(total)

        datalist.remove(Checkout(bangku, "35000"))

        when (bangku){
            "A1" -> statusA1 = false
            "A2" -> statusA2 = false
            "A3" -> statusA3 = false
            "A4" -> statusA4 = false
            "B1" -> statusB1 = false
            "B2" -> statusB2 = false
            "B3" -> statusB3 = false
            "B4" -> statusB4 = false
            "C1" -> statusC1 = false
            "C2" -> statusC2 = false
            "C3" -> statusC3 = false
            "C4" -> statusC4 = false
            "D1" -> statusD1 = false
            "D2" -> statusD2 = false
            "D3" -> statusD3 = false
            "D4" -> statusD4 = false
            else -> ""
        }
    }


    private fun beliTiket(total: Int) {
        if (total == 0){
            btn_pilih.text = "BELI TIKET"
            btn_pilih.visibility = View.INVISIBLE
        } else{
            btn_pilih.text = "BELI TIKET($total)"
            btn_pilih.visibility = View.VISIBLE
        }
    }
}