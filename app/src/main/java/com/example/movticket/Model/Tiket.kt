package com.example.movticket.Model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tiket(
    var judul: String? = "",
    var genre: String? = "",
    var rating: String? = "",
    var poster: String? = "",
    var id : String? = ""
) : Parcelable{

}