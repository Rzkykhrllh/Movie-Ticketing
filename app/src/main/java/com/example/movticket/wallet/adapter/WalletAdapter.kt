package com.example.movticket.wallet.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movticket.R
import com.example.movticket.wallet.model.wallet
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.row_item_now_playing.view.*
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*

class WalletAdapter(private var data: List<wallet>,
                    private val listener: (wallet) -> Unit)
    : RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tv_movie)
        val tvDate: TextView = view.findViewById(R.id.tv_date)
        val tvMoney: TextView = view.findViewById(R.id.tv_money)

        fun bindItem(data:wallet, listener:(wallet) -> Unit, context: Context){
            tvJudul.text = data.title
            tvDate.text = data.date

            val localID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)

            if (data.status.equals("0")){
                tvMoney.text = "-"+formatRupiah.format(data.money)
            } else{
                tvMoney.text = "+"+formatRupiah.format(data.money)
                tvMoney.setTextColor(Color.GREEN)
            }

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_transaksi, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

}
