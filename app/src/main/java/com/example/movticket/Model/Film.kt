package com.example.movticket.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    var nama : String? = "",
    var desc : String? = "",
    var genre : String? = "",
    var director : String? = "",
    var rating : String? = "",
    var poster : String? = "",
) : Parcelable