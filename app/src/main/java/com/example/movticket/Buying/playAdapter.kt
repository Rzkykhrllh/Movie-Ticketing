package com.example.movticket.Buying

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movticket.Model.Plays
import com.example.movticket.R

class playAdapter(private var data: List<Plays>,
                  private val listener: (Plays) -> Unit)
    : RecyclerView.Adapter<playAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tv_nama)
        val imgFoto : ImageView = view.findViewById(R.id.img_foto)

        fun bindItem(data:Plays, listener:(Plays) -> Unit, context: Context){
            tvNama.text = data.nama

            Glide.with(context)
                .load(data.url)
                .into(imgFoto)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_plays, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

}
