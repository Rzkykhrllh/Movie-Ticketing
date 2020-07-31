package com.example.movticket.Home.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import kotlinx.android.synthetic.main.fragment_setting.*

class setting : Fragment() {

    lateinit var preferences : Prefences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Prefences(context!!)

        tv_nama.text = preferences.getValue("nama")
        tv_email.text = preferences.getValue("email")

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(img_profile)

    }


}