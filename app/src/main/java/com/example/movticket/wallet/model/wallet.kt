package com.example.movticket.wallet.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class wallet(
    var title: String? = "",
    var date: String? = "",
    var money: Double? = 0.0,
    var status: String? = ""
    ) : Parcelable{

}