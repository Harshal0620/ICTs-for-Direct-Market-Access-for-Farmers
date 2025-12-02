package com.example.pickfresh.responses

import com.google.gson.annotations.SerializedName

data class RentalResponse(
    var error: Boolean, var message: String, var data: ArrayList<Rentals>,
) {
    data class Rentals(
        @SerializedName("id") var id: String? = null,
        @SerializedName("imageurl") var imageurl: String? = null,
        @SerializedName("rentalType") var rentalType: String? = null,
        @SerializedName("rentalDes") var rentalDes: String? = null,
        @SerializedName("rentalRupees") var rentalRupees: String? = null,
        @SerializedName("dateOn") var dateOn: String? = null,
        @SerializedName("rentalMobile") var rentalMobile: String? = null,
    )
}
