package com.example.movticket.Home.Dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movticket.Model.Film
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var preferences : Prefences
    private lateinit var mDatabase : DatabaseReference
    private var dataList : ArrayList<Film> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Prefences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.setText(preferences.getValue("nama")) //ambil data nama dari preference

        if (preferences.getValue("saldo").equals("")){ //ambil data saldo dari preference
            currency(preferences.getValue("saldo")?.toDouble(), tv_saldo)
        }

        Glide.with(this) //ambil data gambar dari preference
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(img_profile)

        rv_now.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) //ngeset recycler view
        rv_coming.layoutManager = LinearLayoutManager(context) //set recylerview vertical

        getData() // ambil data film dari database
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (getdatasnapshot in dataSnapshot.children){
                    var film =  getdatasnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                dataList.clear()
                for (getdatasnapshot in dataSnapshot.children){
                    var film =  getdatasnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_now.adapter = NowPlayingAdapter(dataList){

                }
               // rv_coming.adapter = ComingSoonAdapter(dataList){
                //}
            }

        })
    }
    private fun currency(harga: Double?, textView: TextView){
        //fungsi untuk konversi currency
        val localID = Locale("in","IDR")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.setText(format.format(harga))
    }

}