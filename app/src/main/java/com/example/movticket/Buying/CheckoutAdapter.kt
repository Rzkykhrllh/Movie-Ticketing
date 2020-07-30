package com.example.movticket.Buying

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movticket.Model.Checkout
import com.example.movticket.R
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.row_item_plays.*
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>,
                      private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvKursi: TextView = view.findViewById(R.id.tv_kursi)
        val tvHarga: TextView = view.findViewById(R.id.tv_hargakursi)

        fun bindItem(data:Checkout, listener:(Checkout) -> Unit, context: Context){

            val localID = Locale("id", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)

            tvHarga.text = formatRupiah.format(data.harga!!.toDouble())

            if (data.kursi!!.startsWith("Total")){
                tvKursi.text = data.kursi
                tvKursi.setCompoundDrawables(null, null,null, null) //supaya kolom terakhir tidak ada gambar kursinya
            } else{
                tvKursi.text = "Seat no. "+data.kursi
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

}
