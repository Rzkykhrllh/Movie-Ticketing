package com.example.movticket.Home.Dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movticket.Model.Film
import com.example.movticket.R
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.row_item_now_playing.view.*

class NowPlayingAdapter(private var data: List<Film>,
                        private val listener: (Film) -> Unit)
    : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tv_judul)
        val tvGenre: TextView = view.findViewById(R.id.tv_genre)
        val tvRating: TextView = view.findViewById(R.id.tv_rate)
        val imgPoster : ImageView = view.findViewById(R.id.img_poster_now)

        fun bindItem(data:Film, listener:(Film) -> Unit, context: Context){
            tvJudul.setText(data.judul)
            tvGenre.setText(data.genre)
            tvRating.setText(data.rating)

            Glide.with(context)
                .load(data.poster)
                .into(imgPoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_now_playing, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

}
