package com.example.movticket.Home.Tiket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movticket.Home.Dashboard.TiketAdapter
import com.example.movticket.Model.Tiket
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tiket.*


class TiketFragment : Fragment() {

    private lateinit var preferences: Prefences //database sementara
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Tiket>()
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Prefences(context!!)
        val username = preferences.getValue("username")
        Log.d("userame", "$username")

        mDatabase = FirebaseDatabase.getInstance().reference
            .child("User")
            .child("$username")
            .child("Tiket")


        rv_tiket.layoutManager = LinearLayoutManager(context!!)
        getData()

    }

    private fun getData() {

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "" + databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val tiket = getdataSnapshot.getValue(Tiket::class.java)
                    dataList.add(tiket!!)
                }
                dataList.reverse()

                rv_tiket.adapter = TiketAdapter(dataList!!) {
                    startActivity(
                        Intent(context, TiketActivity::class.java)
                            .putExtra("data", it)
                    )
                }

                tv_jumlah.text = "${dataList?.size ?: -1} Movies"
            }

        })
    }
}