package com.example.movticket.Buying

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.movticket.Model.Checkout
import com.example.movticket.Model.Film
import com.example.movticket.R
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA1 = false
    var clickedA1 = false
    var statusA2 = false
    var clickedA2 = false
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
                A1.setImageResource(R.drawable.ic_empty)
                statusA1 = false
                total--
                beliTiket(total)
                hapus("A1")
            }else{
                A1.setImageResource(R.drawable.ic_selected)
                statusA1 = true
                total++
                beliTiket(total)

                if (!clickedA1){
                    val data = Checkout("A1", "35000")
                    datalist.add(data)
                }
            }
        }
        A2.setOnClickListener {
            Log.v("A2", "A2 dipencet")
            if (statusA2){
                A2.setImageResource(R.drawable.ic_empty)
                statusA2 = false
                total--
                beliTiket(total)
                hapus("A2")
            }else{
                A2.setImageResource(R.drawable.ic_selected)
                statusA2 = true
                total++
                beliTiket(total)

                if (!clickedA2){
                    val data = Checkout("A2", "35000")
                    datalist.add(data)
                }
            }
        }

        btn_pilih.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java).putExtra("data",datalist))
        }
    }

    private fun hapus(s: String) {

        for ((i,item) in datalist.withIndex()){
            if (item.kursi!!.endsWith(s)){
                datalist.removeAt(i)
                break
            }
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